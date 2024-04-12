package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.exception.UserNotFoundException;
import com.galdino.ufood.domain.model.User;
import com.galdino.ufood.domain.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegisterService {

    private final UserRepository userRepository;

    public UserRegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User add(User user) {
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

        if (!user.matchesPassword(currentPassword)) {
            throw new BusinessException("Current password informed does not match");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
    }
}
