package com.galdino.ufood.api.v1.model;

import com.galdino.ufood.domain.model.UOrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

//@JsonFilter("uOrderFilter")
@Getter
@Setter
public class UOrderSummaryModel {
    private String code;
    private BigDecimal partialAmount;
    private BigDecimal deliveryFee;
    private BigDecimal totalAmount;
    private UOrderStatus status;
    private OffsetDateTime registerDate;
    private RestaurantModelAux restaurant;
//    private UserModel user;
    private String userName;
}
