package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.exception.UGroupNotFoundException;
import com.galdino.ufood.domain.model.UGroup;
import com.galdino.ufood.domain.model.UPermission;
import com.galdino.ufood.domain.repository.UGroupRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UGroupRegisterService {

    private final UGroupRepository uGroupRepository;

    private final UPermissionRegisterService uPermissionRegisterService;

    public UGroupRegisterService(UGroupRepository uGroupRepository, UPermissionRegisterService uPermissionRegisterService) {
        this.uGroupRepository = uGroupRepository;
        this.uPermissionRegisterService = uPermissionRegisterService;
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

    @Transactional
    public void detachUPermission(Long gId, Long pId) {
        UGroup uGroup = findOrThrow(gId);
        UPermission uPermission = uPermissionRegisterService.findOrThrow(pId);

        uGroup.removeUPermission(uPermission);
    }

    @Transactional
    public void attachUPermission(Long gId, Long pId) {
        UGroup uGroup = findOrThrow(gId);
        UPermission uPermission = uPermissionRegisterService.findOrThrow(pId);

        uGroup.addUPermission(uPermission);
    }
}
