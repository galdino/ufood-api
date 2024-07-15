package com.galdino.ufood.api.model;

import com.galdino.ufood.domain.model.UOrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class UOrderModel {
    private String code;
    private BigDecimal partialAmount;
    private BigDecimal deliveryFee;
    private BigDecimal totalAmount;
    private UOrderStatus status;
    private OffsetDateTime registerDate;
    private OffsetDateTime confirmedDate;
    private OffsetDateTime deliveredDate;
    private OffsetDateTime canceledDate;
    private RestaurantModelAux restaurant;
    private UserModel user;
    private PaymentMethodModel paymentMethod;
    private AddressModel address;
    private List<UOrderItemModel> items;
}
