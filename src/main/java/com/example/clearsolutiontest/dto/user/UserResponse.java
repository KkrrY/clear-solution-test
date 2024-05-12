package com.example.clearsolutiontest.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponse extends BaseUserResponse {
    private String lastName;
    private String address;
    private LocalDate birthDate;
    private String phoneNumber;
}
