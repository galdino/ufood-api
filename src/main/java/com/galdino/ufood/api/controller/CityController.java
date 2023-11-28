package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.exceptionhandler.Error;
import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.exception.StateNotFoundException;
import com.galdino.ufood.domain.exception.UEntityNotFoundException;
import com.galdino.ufood.domain.model.City;
import com.galdino.ufood.domain.repository.CityRepository;
import com.galdino.ufood.domain.service.CityRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        return cityRepository.findAll();
    }

    @GetMapping("/{id}")
    public City findById(@PathVariable Long id) {
        return cityRegisterService.findOrThrow(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public City add(@RequestBody City city) {
        try {
            return cityRegisterService.add(city);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public City update(@PathVariable Long id, @RequestBody City city) {
        try {
            City cityAux = cityRegisterService.findOrThrow(id);

            BeanUtils.copyProperties(city, cityAux, "id");
            return cityRegisterService.add(cityAux);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        cityRegisterService.remove(id);
    }

    @ExceptionHandler(UEntityNotFoundException.class)
    public ResponseEntity<?> handleUEntityNotFoundException(UEntityNotFoundException e) {
        Error error = Error.builder()
                           .dateTime(LocalDateTime.now())
                           .message(e.getMessage())
                           .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e) {
        Error error = Error.builder()
                           .dateTime(LocalDateTime.now())
                           .message(e.getMessage())
                           .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


}
