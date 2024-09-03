package com.galdino.ufood.core.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> contentTypeAllowed;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        contentTypeAllowed = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || (value.getContentType() != null && contentTypeAllowed.contains(value.getContentType()));
    }
}
