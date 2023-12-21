package com.galdino.ufood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ValueZeroAddDescriptionValidator implements ConstraintValidator<ValueZeroAddDescription, Object> {
    private String fieldValue;
    private String fieldDescription;
    private String mandatoryDescription;

    @Override
    public void initialize(ValueZeroAddDescription constraintAnnotation) {
        this.fieldValue = constraintAnnotation.fieldValue();
        this.fieldDescription = constraintAnnotation.fieldDescription();
        this.mandatoryDescription = constraintAnnotation.mandatoryDescription();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean isValid = true;

        try {
            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(object.getClass(), this.fieldValue)
                                                     .getReadMethod().invoke(object);

            String description = (String) BeanUtils.getPropertyDescriptor(object.getClass(), this.fieldDescription)
                                                     .getReadMethod().invoke(object);

            if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
                isValid = description.toLowerCase().contains(this.mandatoryDescription.toLowerCase());
            }

            return isValid;

        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
