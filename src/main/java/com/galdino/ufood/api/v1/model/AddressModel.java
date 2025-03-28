package com.galdino.ufood.api.v1.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {
    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String district;
    private CitySummaryModel city;
}
