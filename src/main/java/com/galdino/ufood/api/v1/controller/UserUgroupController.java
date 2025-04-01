package com.galdino.ufood.api.v1.controller;

import com.galdino.ufood.api.v1.assembler.GenericAssembler;
import com.galdino.ufood.api.v1.model.UGroupModel;
import com.galdino.ufood.domain.model.User;
import com.galdino.ufood.domain.service.UserRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/users/{uId}/ugroups", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserUgroupController {

    private final UserRegisterService userRegisterService;

    private final GenericAssembler genericAssembler;

    public UserUgroupController(UserRegisterService userRegisterService, GenericAssembler genericAssembler) {
        this.userRegisterService = userRegisterService;
        this.genericAssembler = genericAssembler;
    }

    @GetMapping
    public List<UGroupModel> list(@PathVariable Long uId) {
        User user = userRegisterService.findOrThrow(uId);
        return genericAssembler.toCollection(user.getUGroups(), UGroupModel.class);
    }

    @DeleteMapping("/{gId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detach(@PathVariable Long uId, @PathVariable Long gId) {
        userRegisterService.detachUGroup(uId, gId);
    }

    @PutMapping("/{gId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attach(@PathVariable Long uId, @PathVariable Long gId) {
        userRegisterService.attachUGroup(uId, gId);
    }


}
