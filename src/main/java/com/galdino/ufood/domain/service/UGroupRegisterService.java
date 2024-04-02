package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.exception.UGroupNotFoundException;
import com.galdino.ufood.domain.model.UGroup;
import com.galdino.ufood.domain.repository.UGroupRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UGroupRegisterService {

    private UGroupRepository uGroupRepository;

    public UGroupRegisterService(UGroupRepository uGroupRepository) {
        this.uGroupRepository = uGroupRepository;
    }

    @Transactional
    public UGroup add(UGroup uGroup) {
        return uGroupRepository.save(uGroup);
    }

    public UGroup findOrThrow(Long id) {
        return uGroupRepository.findById(id).orElseThrow(() -> new UGroupNotFoundException(id));
    }

    @Transactional
    public void remove(Long id) {
        try {
            uGroupRepository.deleteById(id);
            uGroupRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UGroupNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("UGroup with id %d cannot be removed, it is in use.", id));
        }
    }

}
