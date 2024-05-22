package com.example.clearsolutiontest.dto.user;

import com.example.clearsolutiontest.annotation.AgeGreaterThan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

import static com.example.clearsolutiontest.constants.ErrorMessage.*;

@Data
public class UpdateUserRequest {
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9_]+@[a-zA-Z]+\\.[a-zA-Z]{2,5}$"
            , message = INCORRECT_EMAIL)
    private String email;

    @NotBlank (message = EMPTY_FIRST_NAME)
    private String firstName;

    @NotBlank (message = EMPTY_LAST_NAME)
    private String lastName;

    @NotNull( message = EMPTY_BIRTH_DATE)
    @Past(message = INCORRECT_BIRTH_DATE)
    @AgeGreaterThan(valueFromProperties = "age.min")
    private LocalDate birthDate;

    private String address;

    @Pattern(regexp="[0-9]+",
            message = INCORRECT_PHONE_NUMBER)
    private String phoneNumber;
}
