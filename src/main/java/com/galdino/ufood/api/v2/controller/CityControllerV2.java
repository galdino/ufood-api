package com.galdino.ufood.api.v2.controller;

import com.galdino.ufood.api.v1.assembler.GenericAssembler;
import com.galdino.ufood.api.v2.model.CityInputV2;
import com.galdino.ufood.api.v2.model.CityModelV2;
import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.exception.StateNotFoundException;
import com.galdino.ufood.domain.model.City;
import com.galdino.ufood.domain.repository.CityRepository;
import com.galdino.ufood.domain.service.CityRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cities", produces = "application/vnd.ufood.v2+json")
public class CityControllerV2 {

    private CityRepository cityRepository;
    private CityRegisterService cityRegisterService;
    private GenericAssembler genericAssembler;

    public CityControllerV2(CityRepository cityRepository, CityRegisterService cityRegister,
                            GenericAssembler genericAssembler) {
        this.cityRepository = cityRepository;
        this.cityRegisterService = cityRegister;
        this.genericAssembler = genericAssembler;
    }

    @GetMapping
    public List<CityModelV2> list() {
        return genericAssembler.toCollection(cityRepository.findAll(), CityModelV2.class);
    }

    @GetMapping("/{id}")
    public CityModelV2 findById(@PathVariable Long id) {
        City city = cityRegisterService.findOrThrow(id);
        return genericAssembler.toClass(city, CityModelV2.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityModelV2 add(@RequestBody @Valid CityInputV2 cityInput) {
        try {
            City city = genericAssembler.toClass(cityInput, City.class);
            city = cityRegisterService.add(city);
            return genericAssembler.toClass(city, CityModelV2.class);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CityModelV2 update(@PathVariable Long id,
                            @RequestBody @Valid CityInputV2 cityInput) {
        try {
            City cityAux = cityRegisterService.findOrThrow(id);

//            BeanUtils.copyProperties(cityInput, cityAux, "id");
            genericAssembler.copyToObject(cityInput, cityAux);

            cityAux = cityRegisterService.add(cityAux);
            return genericAssembler.toClass(cityAux, CityModelV2.class);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        cityRegisterService.remove(id);
    }

}
