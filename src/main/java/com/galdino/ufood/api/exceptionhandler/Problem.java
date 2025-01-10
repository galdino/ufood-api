package com.galdino.ufood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;
    @ApiModelProperty(example = "2025-01-10T21:11:52.1117439", position = 5)
    private LocalDateTime time;
    @ApiModelProperty(example = "https://ufood.com/invalid-field", position = 10)
    private String type;
    @ApiModelProperty(example = "Invalid field", position = 15)
    private String title;
    @ApiModelProperty(example = "One or more fields are invalid.", position = 20)
    private String detail;
    @ApiModelProperty(example = "One or more fields are invalid.", position = 25)
    private String userMessage;
    @ApiModelProperty(position = 30)
    private List<Object> objects;

    @ApiModel("ProblemObject")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty(example = "state")
        private String name;
        @ApiModelProperty(example = "state must not be null")
        private String userMessage;
    }

}
