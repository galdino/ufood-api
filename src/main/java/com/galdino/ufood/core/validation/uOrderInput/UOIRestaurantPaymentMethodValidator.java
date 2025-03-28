package com.galdino.ufood.core.validation.uOrderInput;

import com.galdino.ufood.api.v1.model.UOrderInput;
import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.model.PaymentMethod;
import com.galdino.ufood.domain.model.Restaurant;

import java.util.Optional;

public class UOIRestaurantPaymentMethodValidator implements UOrderInputValidator {
    
    private final Optional<Restaurant> restaurant;
    private final Optional<PaymentMethod> paymentMethod;
    public UOIRestaurantPaymentMethodValidator(Optional<Restaurant> restaurant, Optional<PaymentMethod> paymentMethod) {
        this.restaurant = restaurant;
        this.paymentMethod = paymentMethod;
    }

    @Override
    public void validate(UOrderInput uOrderInput) {
        if (restaurant.isPresent() && paymentMethod.isPresent()) {
            Restaurant restaurantAux = restaurant.get();
            PaymentMethod paymentMethodAux = paymentMethod.get();

            if (!restaurantAux.getPaymentMethods().contains(paymentMethodAux)) {
                throw new BusinessException(String.format("This restaurant does not accept %s payment method", paymentMethodAux.getDescription()));
            }
        }       
    }
}
