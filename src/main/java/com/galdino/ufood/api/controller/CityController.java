package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.model.CityInput;
import com.galdino.ufood.api.model.CityModel;
import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.exception.StateNotFoundException;
import com.galdino.ufood.domain.model.City;
import com.galdino.ufood.domain.repository.CityRepository;
import com.galdino.ufood.domain.service.CityRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cities")
@RestController
@RequestMapping("/cities")
public class CityController {

    private CityRepository cityRepository;
    private CityRegisterService cityRegisterService;
    private GenericAssembler genericAssembler;

    public CityController(CityRepository cityRepository, CityRegisterService cityRegister,
                          GenericAssembler genericAssembler) {
        this.cityRepository = cityRepository;
        this.cityRegisterService = cityRegister;
        this.genericAssembler = genericAssembler;
    }

    @ApiOperation("List all cities")
    @GetMapping
    public List<CityModel> list() {
        return  genericAssembler.toCollection(cityRepository.findAll(), CityModel.class);
    }

    @ApiOperation("Find city by id")
    @GetMapping("/{id}")
    public CityModel findById(@ApiParam(value = "City id", example = "1")
                              @PathVariable Long id) {
        City city = cityRegisterService.findOrThrow(id);
        return genericAssembler.toClass(city, CityModel.class);
    }

    @ApiOperation("Create city")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityModel add(@ApiParam(name = "body", value = "New city representation")
                         @RequestBody @Valid CityInput cityInput) {
        try {
            City city = genericAssembler.toClass(cityInput, City.class);
            city = cityRegisterService.add(city);
            return genericAssembler.toClass(city, CityModel.class);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @ApiOperation("Update city by id")
    @PutMapping("/{id}")
    public CityModel update(@ApiParam(value = "City id", example = "1")
                            @PathVariable Long id,

                            @ApiParam(name = "body", value = "New city representation with new data")
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

    @ApiOperation("Remove city by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@ApiParam(value = "City id", example = "1")
                       @PathVariable Long id) {
        cityRegisterService.remove(id);
    }

}
