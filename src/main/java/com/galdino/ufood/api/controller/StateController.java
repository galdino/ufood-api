package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.model.StateInput;
import com.galdino.ufood.api.model.StateModel;
import com.galdino.ufood.domain.model.State;
import com.galdino.ufood.domain.repository.StateRepository;
import com.galdino.ufood.domain.service.StateRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {

    private StateRepository stateRepository;
    private StateRegisterService stateRegisterService;
    private GenericAssembler genericAssembler;

    public StateController(StateRepository stateRepository, StateRegisterService stateRegisterService,
                           GenericAssembler genericAssembler) {
        this.stateRepository = stateRepository;
        this.stateRegisterService = stateRegisterService;
        this.genericAssembler = genericAssembler;
    }

    @GetMapping
    public List<StateModel> list() {
        return genericAssembler.toCollection(stateRepository.findAll(), StateModel.class);
    }

    @GetMapping("/{id}")
    public StateModel findById(@PathVariable Long id) {
        State state = stateRegisterService.findOrThrow(id);
        return genericAssembler.toClass(state, StateModel.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateModel add(@RequestBody @Valid StateInput stateInput) {
        State state = genericAssembler.toClass(stateInput, State.class);
        state = stateRegisterService.add(state);
        return genericAssembler.toClass(state, StateModel.class);
    }

    @PutMapping("/{id}")
    public StateModel update(@PathVariable Long id, @RequestBody @Valid StateInput stateInput) {
        State stateAux = stateRegisterService.findOrThrow(id);

//        BeanUtils.copyProperties(state, stateAux, "id");
        genericAssembler.copyToObject(stateInput, stateAux);

        stateAux = stateRegisterService.add(stateAux);

        return genericAssembler.toClass(stateAux, StateModel.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        stateRegisterService.remove(id);
    }
}
