package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.model.UPermissionModel;
import com.galdino.ufood.domain.model.UGroup;
import com.galdino.ufood.domain.service.UGroupRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ugroups/{gId}/upermissons")
public class UgroupUPermissionController {

    private final UGroupRegisterService ugroupRegisterService;
    private final GenericAssembler genericAssembler;

    public UgroupUPermissionController(UGroupRegisterService ugroupRegisterService, GenericAssembler genericAssembler) {
        this.ugroupRegisterService = ugroupRegisterService;
        this.genericAssembler = genericAssembler;
    }

    @GetMapping
    public List<UPermissionModel> list(@PathVariable Long gId) {
        UGroup uGroup = ugroupRegisterService.findOrThrow(gId);
        return genericAssembler.toCollection(uGroup.getUPermissions(), UPermissionModel.class);
    }

    @DeleteMapping("/{pId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detach(@PathVariable Long gId, @PathVariable Long pId) {
        ugroupRegisterService.detachUPermission(gId, pId);
    }

    @PutMapping("/{pId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attach(@PathVariable Long gId, @PathVariable Long pId) {
        ugroupRegisterService.attachUPermission(gId, pId);
    }

}
