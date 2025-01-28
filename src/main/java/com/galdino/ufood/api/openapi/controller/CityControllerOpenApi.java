package com.galdino.ufood.api.openapi.controller;

import com.galdino.ufood.api.exceptionhandler.Problem;
import com.galdino.ufood.api.model.CityInput;
import com.galdino.ufood.api.model.CityModel;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Cities")
public interface CityControllerOpenApi {

    @ApiOperation("List all cities")
    public List<CityModel> list();

    @ApiOperation("Find city by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid Parameter", response = Problem.class),
            @ApiResponse(code = 404, message = "City not found", response = Problem.class)
    })
    public CityModel findById(@ApiParam(value = "City id", example = "1")
                              Long id);

    @ApiOperation("Create city")
    @ApiResponses({
            @ApiResponse(code = 201, message = "City created")
    })
    public CityModel add(@ApiParam(name = "body", value = "New city representation")
                         CityInput cityInput);

    @ApiOperation("Update city by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "City updated"),
            @ApiResponse(code = 404, message = "City not found", response = Problem.class)
    })
    public CityModel update(@ApiParam(value = "City id", example = "1")
                            Long id,
                            @ApiParam(name = "body", value = "New city representation with new data")
                            CityInput cityInput);

    @ApiOperation("Remove city by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "City removed"),
            @ApiResponse(code = 404, message = "City not found", response = Problem.class)
    })
    public void remove(@ApiParam(value = "City id", example = "1")
                       Long id);

}
