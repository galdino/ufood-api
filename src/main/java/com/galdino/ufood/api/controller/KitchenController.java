package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.model.KitchensXmlWrapper;
import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.repository.KitchenRepository;
import com.galdino.ufood.domain.service.KitchenRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/kitchens")
public class KitchenController {

    private KitchenRepository kitchenRepository;
    private KitchenRegisterService kitchenRegisterService;

    public KitchenController(KitchenRepository kitchenRepository, KitchenRegisterService kitchenRegisterService) {
        this.kitchenRepository = kitchenRepository;
        this.kitchenRegisterService = kitchenRegisterService;
    }

    @GetMapping
    public List<Kitchen> list() {
        return kitchenRepository.findAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public KitchensXmlWrapper listXml() {
        return new KitchensXmlWrapper(kitchenRepository.findAll());
    }

//    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public ResponseEntity<Kitchen> findById(@PathVariable Long id) {
        Optional<Kitchen> kitchen = kitchenRepository.findById(id);

//        return ResponseEntity.status(HttpStatus.OK).body(kitchen);
//        return ResponseEntity.ok(kitchen);

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.LOCATION, "http://localhost:8080/kitchens/");
//
//        return ResponseEntity.status(HttpStatus.FOUND)
//                             .headers(httpHeaders)
//                             .build();

        if (kitchen.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(kitchen.get());
    }

    @PostMapping
    public ResponseEntity<Kitchen> add(@RequestBody Kitchen kitchen) {
        Kitchen kitchenAux = kitchenRegisterService.add(kitchen);

        return ResponseEntity.status(HttpStatus.CREATED).body(kitchenAux);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kitchen> update(@PathVariable Long id, @RequestBody Kitchen kitchen) {
        Optional<Kitchen> kitchenAux = kitchenRepository.findById(id);

        if (kitchenAux.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(kitchen, kitchenAux.get(), "id");

        Kitchen added = kitchenRegisterService.add(kitchenAux.get());

        return ResponseEntity.status(HttpStatus.OK).body(added);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Kitchen> delete(@PathVariable Long id) {
        try {
            kitchenRegisterService.remove(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
