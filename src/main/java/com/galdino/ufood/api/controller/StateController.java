package com.galdino.ufood.api.controller;

import com.galdino.ufood.domain.model.State;
import com.galdino.ufood.domain.repository.StateRepository;
import com.galdino.ufood.domain.service.StateRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {

    private StateRepository stateRepository;
    private StateRegisterService stateRegisterService;

    public StateController(StateRepository stateRepository, StateRegisterService stateRegisterService) {
        this.stateRepository = stateRepository;
        this.stateRegisterService = stateRegisterService;
    }

    @GetMapping
    public List<State> list() {
        return stateRepository.findAll();
    }

    @GetMapping("/{id}")
    public State findById(@PathVariable Long id) {
        return stateRegisterService.findOrThrow(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State add(@RequestBody @Valid State state) {
        return stateRegisterService.add(state);
    }

    @PutMapping("/{id}")
    public State update(@PathVariable Long id, @RequestBody @Valid State state) {
        State stateAux = stateRegisterService.findOrThrow(id);

        BeanUtils.copyProperties(state, stateAux, "id");

        return stateRegisterService.add(stateAux);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        stateRegisterService.remove(id);
    }
}
