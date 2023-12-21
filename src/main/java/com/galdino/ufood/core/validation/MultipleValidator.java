package com.galdino.ufood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

    private int multipleNumber;
    @Override
    public void initialize(Multiple constraintAnnotation) {
        this.multipleNumber = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (value != null) {
            var valueDecimal = BigDecimal.valueOf(value.doubleValue());
            var multipleDecimal = BigDecimal.valueOf(this.multipleNumber);
            BigDecimal remainder = valueDecimal.remainder(multipleDecimal);

            isValid = BigDecimal.ZERO.compareTo(remainder) == 0;
        }

        return isValid;
    }
}
