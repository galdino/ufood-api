package com.galdino.ufood.core.validation.uOrderInput;

import com.galdino.ufood.api.model.UOrderInput;
import com.galdino.ufood.domain.exception.PaymentMethodNotFoundException;
import com.galdino.ufood.domain.model.PaymentMethod;

import java.util.Optional;

public class UOIPaymentMethodValidator implements UOrderInputValidator {

    private final Optional<PaymentMethod> paymentMethod;

    public UOIPaymentMethodValidator(Optional<PaymentMethod> paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    @Override
    public void validate(UOrderInput uOrderInput) {
        if (paymentMethod.isEmpty()) {
            throw new PaymentMethodNotFoundException(uOrderInput.getPaymentMethod().getId());
        }
    }
}
