package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.controller.openapi.CityControllerOpenApi;
import com.galdino.ufood.api.model.CityInput;
import com.galdino.ufood.api.model.CityModel;
import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.exception.StateNotFoundException;
import com.galdino.ufood.domain.model.City;
import com.galdino.ufood.domain.repository.CityRepository;
import com.galdino.ufood.domain.service.CityRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController implements CityControllerOpenApi {

    private CityRepository cityRepository;
    private CityRegisterService cityRegisterService;
    private GenericAssembler genericAssembler;

    public CityController(CityRepository cityRepository, CityRegisterService cityRegister,
                          GenericAssembler genericAssembler) {
        this.cityRepository = cityRepository;
        this.cityRegisterService = cityRegister;
        this.genericAssembler = genericAssembler;
    }

    @GetMapping
    public List<CityModel> list() {
        return  genericAssembler.toCollection(cityRepository.findAll(), CityModel.class);
    }

    @GetMapping("/{id}")
    public CityModel findById(@PathVariable Long id) {
        City city = cityRegisterService.findOrThrow(id);
        return genericAssembler.toClass(city, CityModel.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityModel add(@RequestBody @Valid CityInput cityInput) {
        try {
            City city = genericAssembler.toClass(cityInput, City.class);
            city = cityRegisterService.add(city);
            return genericAssembler.toClass(city, CityModel.class);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CityModel update(@PathVariable Long id,
                            @RequestBody @Valid CityInput cityInput) {
        try {
            City cityAux = cityRegisterService.findOrThrow(id);

//            BeanUtils.copyProperties(cityInput, cityAux, "id");
            genericAssembler.copyToObject(cityInput, cityAux);

            cityAux = cityRegisterService.add(cityAux);
            return genericAssembler.toClass(cityAux, CityModel.class);
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
