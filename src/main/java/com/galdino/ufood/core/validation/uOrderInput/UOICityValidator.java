package com.galdino.ufood.core.validation.uOrderInput;

import com.galdino.ufood.api.v1.model.UOrderInput;
import com.galdino.ufood.domain.exception.CityNotFoundException;
import com.galdino.ufood.domain.model.City;

import java.util.Optional;

public class UOICityValidator implements UOrderInputValidator {

    public final Optional<City> city;
    public UOICityValidator(Optional<City> city) {
        this.city = city;
    }

    @Override
    public void validate(UOrderInput uOrderInput) {
        if (city.isEmpty()) {
            throw new CityNotFoundException(uOrderInput.getAddress().getCity().getId());
        }
    }
}
