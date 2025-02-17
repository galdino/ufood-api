package com.galdino.ufood.api.openapi.controller;

import com.galdino.ufood.api.exceptionhandler.Problem;
import com.galdino.ufood.api.model.KitchenInput;
import com.galdino.ufood.api.model.KitchenModel;
import com.galdino.ufood.api.model.KitchensXmlWrapper;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Kitchens")
public interface KitchenControllerOpenApi {

    @ApiOperation("List all kitchens")
    public Page<KitchenModel> list(Pageable pageable);

    @ApiOperation("List all kitchens in XML format")
    public KitchensXmlWrapper listXml();

    @ApiOperation("Find kitchen by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid Parameter", response = Problem.class),
            @ApiResponse(code = 404, message = "Kitchen not found", response = Problem.class)
    })
    public KitchenModel findById(@ApiParam(value = "Kitchen id", example = "1") Long id);

    @ApiOperation("Create kitchen")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Kitchen created")
    })
    public KitchenModel add(@ApiParam(name = "body", value = "New kitchen representation") KitchenInput kitchenInput);

    @ApiOperation("Update kitchen by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Kitchen updated"),
            @ApiResponse(code = 404, message = "Kitchen not found", response = Problem.class)
    })
    public KitchenModel update(@ApiParam(value = "Kitchen id", example = "1") Long id,
                               @ApiParam("New kitchen representation with new data") KitchenInput kitchenInput);

    @ApiOperation("Remove kitchen by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Kitchen removed"),
            @ApiResponse(code = 404, message = "Kitchen not found", response = Problem.class)
    })
    public void delete(@ApiParam(value = "Kitchen id", example = "1") Long id);

}
