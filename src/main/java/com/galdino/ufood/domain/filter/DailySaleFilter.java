package com.galdino.ufood.domain.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

public class DailySaleFilter {
    private Long restaurantId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime initialRegisterDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime finalRegisterDate;
}
