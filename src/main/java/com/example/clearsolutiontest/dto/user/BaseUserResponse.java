package com.example.clearsolutiontest.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseUserResponse {
    private Long id;
    private String email;
    private String firstName;
}
