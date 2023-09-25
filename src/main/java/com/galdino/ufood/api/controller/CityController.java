package com.galdino.ufood.api.controller;

import com.galdino.ufood.domain.model.City;
import com.galdino.ufood.domain.repository.CityRepository;
import com.galdino.ufood.domain.service.CityRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private CityRepository cityRepository;
    private CityRegisterService cityRegisterService;

    public CityController(CityRepository cityRepository, CityRegisterService cityRegister) {
        this.cityRepository = cityRepository;
        this.cityRegisterService = cityRegister;
    }

    @GetMapping
    public List<City> list() {
        return cityRepository.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable Long id) {
        City city = cityRepository.findById(id);

        if (city == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(city);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody City city) {
        try {
            City cityAux = cityRegisterService.add(city);
            return ResponseEntity.status(HttpStatus.CREATED).body(cityAux);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody City city) {
        City cityAux = cityRepository.findById(id);

        if (cityAux == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        BeanUtils.copyProperties(city, cityAux, "id");

        try {
            cityAux = cityRegisterService.add(cityAux);
            return ResponseEntity.status(HttpStatus.OK).body(cityAux);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<City> remove(@PathVariable Long id) {
        try {
            cityRegisterService.remove(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
