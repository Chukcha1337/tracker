package com.chuckcha.tt.core.user;

import com.chuckcha.tt.core.user.validation.PasswordsMatch;
import jakarta.validation.constraints.*;

@PasswordsMatch
public record RegistrationRequest(
        @NotBlank(message = "Username cannot be empty")
        @Size(min = 3, max = 30, message = "Must be {min}-{max} chars")
        @Pattern(
                regexp = "^[a-zA-Z0-9]+\\w*[a-zA-Z0-9]+$",
                message = "Invalid format"
        )
        String username,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 5, max = 20, message = "Password must be  {min}-{max} chars")
        @Pattern(
                regexp = "^[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>\\[\\]/`~+=\\-_';]*$",
                message = "Invalid characters"
        )
        String password,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 5, max = 20, message = "Password must be  {min}-{max} chars")
        @Pattern(
                regexp = "^[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>\\[\\]/`~+=\\-_';]*$",
                message = "Invalid characters"
        )
        String confirmatedPassword,

        @NotBlank
        @NotEmpty
        @NotNull
        @Email(message = "Customer Email is not a valid email address")
        String email
        ) {
}
