package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.UPermissionNotFoundException;
import com.galdino.ufood.domain.model.UPermission;
import com.galdino.ufood.domain.repository.UPermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class UPermissionRegisterService {

    private final UPermissionRepository uPermissionRepository;

    public UPermissionRegisterService(UPermissionRepository uPermissionRepository) {
        this.uPermissionRepository = uPermissionRepository;
    }

    public UPermission findOrThrow(Long id) {
        return uPermissionRepository.findById(id).orElseThrow(() -> new UPermissionNotFoundException(id));
    }

}
