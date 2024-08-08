package com.galdino.ufood.domain.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
public class UOrderFilter {
    private Long userId;
    private Long restaurantId;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime initialRegisterDate;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime finalRegisterDate;
}
