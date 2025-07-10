package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.exception.UserNotFoundException;
import com.galdino.ufood.domain.model.UGroup;
import com.galdino.ufood.domain.model.User;
import com.galdino.ufood.domain.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserRegisterService {

    private final UserRepository userRepository;
    private final UGroupRegisterService uGroupRegisterService;
    private final PasswordEncoder passwordEncoder;

    public UserRegisterService(UserRepository userRepository, UGroupRegisterService uGroupRegisterService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.uGroupRegisterService = uGroupRegisterService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User add(User user) {

        userRepository.detach(user);

        Optional<User> userAux = userRepository.findByEmail(user.getEmail());

        if (userAux.isPresent() && !userAux.get().equals(user)) {
            throw new BusinessException(String.format("Email %s already registered", user.getEmail()));
        }

        if (user.isNew()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    public User findOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public void remove(Long id) {
        try {
            userRepository.deleteById(id);
            userRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(id);
        }
    }

    @Transactional
    public void updatePassword(Long id, String currentPassword, String newPassword) {
        User user = findOrThrow(id);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BusinessException("Current password informed does not match");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public void detachUGroup(Long uId, Long gId) {
        User user = findOrThrow(uId);
        UGroup uGroup = uGroupRegisterService.findOrThrow(gId);

        user.removeUGroup(uGroup);
    }

    @Transactional
    public void attachUGroup(Long uId, Long gId) {
        User user = findOrThrow(uId);
        UGroup uGroup = uGroupRegisterService.findOrThrow(gId);

        user.addUGroup(uGroup);
    }
}
