package com.galdino.ufood.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( {ElementType.TYPE} )
@Retention(RUNTIME)
@Constraint(validatedBy = { ValueZeroAddDescriptionValidator.class })
public @interface ValueZeroAddDescription {

    String message() default "mandatory description is invalid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };


    String fieldValue();

    String fieldDescription();

    String mandatoryDescription();
}
