package com.galdino.ufood.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("Pageable")
@Getter
@Setter
public class PageableModelOpenApi {
    @ApiModelProperty(example = "0", value = "page number")
    private int page;

    @ApiModelProperty(example = "10", value = "number of items per page")
    private int size;

    @ApiModelProperty(example = "name, asc", value = "sort property name")
    private List<String> sort;
}
