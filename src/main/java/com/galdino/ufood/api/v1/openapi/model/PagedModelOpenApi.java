package com.galdino.ufood.api.v1.openapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedModelOpenApi<T> {
    private List<T> content;

    @ApiModelProperty(example = "10", value = "number of items per page")
    private Long size;

    @ApiModelProperty(example = "50", value = "number of elements")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "number of pages")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "page number")
    private Long number;
}
