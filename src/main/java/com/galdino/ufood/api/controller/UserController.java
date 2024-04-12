package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.model.PasswordUpdateInput;
import com.galdino.ufood.api.model.UserInput;
import com.galdino.ufood.api.model.UserInputAux;
import com.galdino.ufood.api.model.UserModel;
import com.galdino.ufood.domain.model.User;
import com.galdino.ufood.domain.repository.UserRepository;
import com.galdino.ufood.domain.service.UserRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserRegisterService userRegisterService;
    private final GenericAssembler genericAssembler;

    public UserController(UserRepository userRepository, UserRegisterService userRegisterService,
                          GenericAssembler genericAssembler) {
        this.userRepository = userRepository;
        this.userRegisterService = userRegisterService;
        this.genericAssembler = genericAssembler;
    }

    @GetMapping
    public List<UserModel> list() {
        return genericAssembler.toCollection(userRepository.findAll(), UserModel.class);
    }

    @GetMapping("/{id}")
    public UserModel findById(@PathVariable Long id) {
        User user = userRegisterService.findOrThrow(id);
        return genericAssembler.toClass(user, UserModel.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel add(@RequestBody @Valid UserInput userInput) {
        User user = genericAssembler.toClass(userInput, User.class);
        user = userRegisterService.add(user);
        return genericAssembler.toClass(user, UserModel.class);
    }

    @PutMapping("/{id}")
    public UserModel update(@PathVariable Long id, @RequestBody @Valid UserInputAux userInputAux) {
        User user = userRegisterService.findOrThrow(id);
        genericAssembler.copyToObject(userInputAux, user);
        user = userRegisterService.add(user);
        return genericAssembler.toClass(user, UserModel.class);
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Long id, @RequestBody @Valid PasswordUpdateInput passwordUpdateInput) {
        userRegisterService.updatePassword(id, passwordUpdateInput.getCurrentPassword(), passwordUpdateInput.getNewPassword());
    }

}
