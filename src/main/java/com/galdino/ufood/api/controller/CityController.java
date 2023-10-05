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
import java.util.Optional;

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
        return cityRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable Long id) {
        Optional<City> city = cityRepository.findById(id);

        if (city.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(city.get());
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
        Optional<City> cityAux = cityRepository.findById(id);

        if (cityAux.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        BeanUtils.copyProperties(city, cityAux.get(), "id");

        try {
            City added = cityRegisterService.add(cityAux.get());
            return ResponseEntity.status(HttpStatus.OK).body(added);
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
