package com.chuckcha.tt.core.user.validation;

import com.chuckcha.tt.core.user.RegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, RegistrationRequest> {

    @Override
    public boolean isValid(RegistrationRequest userRequest, ConstraintValidatorContext context) {
        if (userRequest == null) return true;

        String password = userRequest.password();
        String confirmatedPassword = userRequest.confirmatedPassword();

        if (password == null || confirmatedPassword == null) return false;

        return password.equals(confirmatedPassword);
    }
}
