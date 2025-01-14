package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.exceptionhandler.Problem;
import com.galdino.ufood.api.model.CityInput;
import com.galdino.ufood.api.model.CityModel;
import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.exception.StateNotFoundException;
import com.galdino.ufood.domain.model.City;
import com.galdino.ufood.domain.repository.CityRepository;
import com.galdino.ufood.domain.service.CityRegisterService;
import io.swagger.annotations.*;
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
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid Parameter", response = Problem.class),
            @ApiResponse(code = 404, message = "City not found", response = Problem.class)
    })
    @GetMapping("/{id}")
    public CityModel findById(@ApiParam(value = "City id", example = "1")
                              @PathVariable Long id) {
        City city = cityRegisterService.findOrThrow(id);
        return genericAssembler.toClass(city, CityModel.class);
    }

    @ApiOperation("Create city")
    @ApiResponses({
            @ApiResponse(code = 201, message = "City created")
    })
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
    @ApiResponses({
            @ApiResponse(code = 200, message = "City updated"),
            @ApiResponse(code = 404, message = "City not found", response = Problem.class)
    })
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
    @ApiResponses({
            @ApiResponse(code = 204, message = "City removed"),
            @ApiResponse(code = 404, message = "City not found", response = Problem.class)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@ApiParam(value = "City id", example = "1")
                       @PathVariable Long id) {
        cityRegisterService.remove(id);
    }

}
