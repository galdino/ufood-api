package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.model.KitchensXmlWrapper;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.repository.KitchenRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/kitchens")
public class KitchenController {

    private KitchenRepository kitchenRepository;

    public KitchenController(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    @GetMapping
    public List<Kitchen> list() {
        return kitchenRepository.list();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public KitchensXmlWrapper listXml() {
        return new KitchensXmlWrapper(kitchenRepository.list());
    }

//    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public ResponseEntity<Kitchen> findById(@PathVariable Long id) {
        Kitchen kitchen = kitchenRepository.findById(id);

//        return ResponseEntity.status(HttpStatus.OK).body(kitchen);
//        return ResponseEntity.ok(kitchen);

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.LOCATION, "http://localhost:8080/kitchens/");
//
//        return ResponseEntity.status(HttpStatus.FOUND)
//                             .headers(httpHeaders)
//                             .build();

        if (kitchen == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(kitchen);
    }

    @PostMapping
    public ResponseEntity<Kitchen> add(@RequestBody Kitchen kitchen) {
        Kitchen kitchenAux = kitchenRepository.add(kitchen);

        return ResponseEntity.status(HttpStatus.CREATED).body(kitchenAux);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kitchen> update(@PathVariable Long id, @RequestBody Kitchen kitchen) {
        Kitchen kitchenAux = kitchenRepository.findById(id);

        if (kitchenAux == null) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(kitchen, kitchenAux, "id");

        kitchenAux = kitchenRepository.add(kitchenAux);

        return ResponseEntity.status(HttpStatus.OK).body(kitchenAux);

    }
}
