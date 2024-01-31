package com.galdino.ufood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.galdino.ufood.domain.model.Address;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.model.PaymentMethod;
import com.galdino.ufood.domain.model.Product;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantMixin {

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private Kitchen kitchen;

    @JsonIgnore
    private Address address;

//    @JsonIgnore
    private OffsetDateTime registerDate;

//    @JsonIgnore
    private OffsetDateTime updateDate;

    @JsonIgnore
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @JsonIgnore
    private List<Product> products = new ArrayList<>();

}
