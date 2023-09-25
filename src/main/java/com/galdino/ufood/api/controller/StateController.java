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
        return stateRepository.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> findById(@PathVariable Long id) {
        State state = stateRepository.findById(id);

        if (state == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(state);
    }

    @PostMapping
    public ResponseEntity<State> add(@RequestBody State state) {
        State stateAux = stateRegisterService.add(state);

        return ResponseEntity.status(HttpStatus.CREATED).body(stateAux);
    }

    @PutMapping("/{id}")
    public ResponseEntity<State> update(@PathVariable Long id, @RequestBody State state) {
        State stateAux = stateRepository.findById(id);

        if (stateAux == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        BeanUtils.copyProperties(state, stateAux, "id");
        stateAux = stateRegisterService.add(stateAux);

        return ResponseEntity.status(HttpStatus.OK).body(stateAux);

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
