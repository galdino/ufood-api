package com.galdino.ufood.api.v1.openapi.controller;

import com.galdino.ufood.api.v1.model.UOrderInput;
import com.galdino.ufood.api.v1.model.UOrderModel;
import com.galdino.ufood.api.v1.model.UOrderSummaryModel;
import com.galdino.ufood.domain.filter.UOrderFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "UOrder")
public interface UOrderControllerOpenApi {

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Fields names for filtering in the response, separated by commas", name = "fields",
                    paramType = "query", type = "string")
    })
    @ApiOperation("Search for uorders")
    public Page<UOrderSummaryModel> search(UOrderFilter filter, Pageable pageable);

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Fields names for filtering in the response, separated by commas", name = "fields",
                    paramType = "query", type = "string")
    })
    public UOrderModel findByCode(String code);

    public UOrderModel add(UOrderInput uOrderInput);

}
