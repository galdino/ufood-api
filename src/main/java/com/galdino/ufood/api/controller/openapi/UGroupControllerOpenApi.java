package com.galdino.ufood.api.controller.openapi;

import com.galdino.ufood.api.exceptionhandler.Problem;
import com.galdino.ufood.api.model.UGroupInput;
import com.galdino.ufood.api.model.UGroupModel;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "UGroup")
public interface UGroupControllerOpenApi {

    @ApiOperation("List all ugroups")
    public List<UGroupModel> list();

    @ApiOperation("Find ugroup by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid Parameter", response = Problem.class),
            @ApiResponse(code = 404, message = "UGroup not found", response = Problem.class)
    })
    public UGroupModel findById(@ApiParam(value = "UGroup id", example = "1") Long id);

    @ApiOperation("Create ugroup")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Ugroup created")
    })
    public UGroupModel add(@ApiParam(name = "body", value = "New ugroup representation")
                           UGroupInput uGroupInput);

    @ApiOperation("Update ugroup by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ugroup updated"),
            @ApiResponse(code = 404, message = "Ugroup not found", response = Problem.class)
    })
    public UGroupModel update(@ApiParam(value = "Ugroup id", example = "1")
                              Long id,
                              @ApiParam(name = "body", value = "New ugroup representation")
                              UGroupInput uGroupInput);

    @ApiOperation("Remove ugroup by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Ugroup removed"),
            @ApiResponse(code = 204, message = "Ugroup not found")
    })
    public void delete(@ApiParam(value = "Ugroup id", example = "1") Long id);
}
