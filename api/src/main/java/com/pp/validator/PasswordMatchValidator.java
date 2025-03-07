package com.pp.validator;

import com.pp.annotation.PasswordMatch;
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
        if (isNullObject(obj, context)) {
            return false;
        }

        BeanWrapper beanWrapper = new BeanWrapperImpl(obj);
        Object password = beanWrapper.getPropertyValue(passwordField);
        Object confirmPassword = beanWrapper.getPropertyValue(confirmPasswordField);

        return isPasswordValid(password, confirmPassword, context);
    }

    private boolean isNullObject(final Object obj, final ConstraintValidatorContext context) {
        if (Objects.isNull(obj)) {
            setConstraintViolation(context, ResponseCode.IS_NULL_PARAM.getMessage());
            return true;
        }
        return false;
    }

    private boolean isPasswordValid(final Object password, final Object confirmPassword, final ConstraintValidatorContext context) {
        if (Objects.isNull(password) || Objects.isNull(confirmPassword) || !password.equals(confirmPassword)) {
            setConstraintViolation(context, ResponseCode.INVALID_PASSWORD.getMessage());
            return false;
        }
        return true;
    }

    private void setConstraintViolation(final ConstraintValidatorContext context, final String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

}
