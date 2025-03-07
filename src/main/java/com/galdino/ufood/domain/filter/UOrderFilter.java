package com.galdino.ufood.domain.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
public class UOrderFilter {
    @ApiModelProperty(example = "1", value = "User id for the search parameter")
    private Long userId;

    @ApiModelProperty(example = "1", value = "Restaurant id for the search parameter")
    private Long restaurantId;

    @ApiModelProperty(example = "2024-07-23T21:00:00", value = "Initial register date/time for the search parameter")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime initialRegisterDate;

    @ApiModelProperty(example = "2024-07-24T21:43:12", value = "Final register date/time for the search parameter")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime finalRegisterDate;
}
