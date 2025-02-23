package com.pp.validator;

import com.pp.annotation.PasswordMatch;
import com.pp.exception.CustomException;
import com.pp.exception.ResponseCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String passwordField;
    private String confirmPasswordField;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.passwordField = constraintAnnotation.password();
        this.confirmPasswordField = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (Objects.isNull(obj)) {
            throw new CustomException(ResponseCode.IS_NULL_PARAM);
        }

        BeanWrapper beanWrapper = new BeanWrapperImpl(obj);
        Object password = beanWrapper.getPropertyValue(passwordField);
        Object confirmPassword = beanWrapper.getPropertyValue(confirmPasswordField);
        return !Objects.isNull(password) && !Objects.isNull(confirmPassword) && password.equals(confirmPassword);

    }

}
