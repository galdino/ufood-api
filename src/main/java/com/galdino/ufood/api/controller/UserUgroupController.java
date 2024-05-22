package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.model.UGroupModel;
import com.galdino.ufood.domain.model.User;
import com.galdino.ufood.domain.service.UserRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{uId}/ugroups")
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
