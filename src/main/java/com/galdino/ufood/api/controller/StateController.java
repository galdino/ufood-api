package com.galdino.ufood.api.controller;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.model.State;
import com.galdino.ufood.domain.repository.StateRepository;
import com.galdino.ufood.domain.service.StateRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<State> findById(@PathVariable Long id) {
        Optional<State> state = stateRepository.findById(id);

        if (state.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(state.get());
    }

    @PostMapping
    public ResponseEntity<State> add(@RequestBody State state) {
        State stateAux = stateRegisterService.add(state);

        return ResponseEntity.status(HttpStatus.CREATED).body(stateAux);
    }

    @PutMapping("/{id}")
    public ResponseEntity<State> update(@PathVariable Long id, @RequestBody State state) {
        Optional<State> stateAux = stateRepository.findById(id);

        if (stateAux.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        BeanUtils.copyProperties(state, stateAux.get(), "id");
        State added = stateRegisterService.add(stateAux.get());

        return ResponseEntity.status(HttpStatus.OK).body(added);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            stateRegisterService.remove(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }
}
