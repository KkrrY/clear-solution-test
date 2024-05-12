package com.example.clearsolutiontest.mapper;

import com.example.clearsolutiontest.domain.User;
import com.example.clearsolutiontest.dto.RegistrationRequest;
import com.example.clearsolutiontest.dto.user.UpdateUserRequest;
import com.example.clearsolutiontest.dto.user.UserResponse;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.clearsolutiontest.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void convertUserDtoToEntity() {
        UpdateUserRequest userRequest = new UpdateUserRequest();
        userRequest.setFirstName(FIRST_NAME);

        User user = modelMapper.map(userRequest, User.class);
        assertEquals(userRequest.getFirstName(), user.getFirstName());
    }

    @Test
    public void convertRegistrationRequestDtoToEntity() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName(FIRST_NAME);
        registrationRequest.setEmail(USER_EMAIL);

        User user = modelMapper.map(registrationRequest, User.class);
        assertEquals(registrationRequest.getFirstName(), user.getFirstName());
        assertEquals(registrationRequest.getEmail(), user.getEmail());
    }

    @Test
    public void convertToResponseDto() {
        User user = new User();
        user.setFirstName(FIRST_NAME);
        user.setEmail(USER_EMAIL);

        UserResponse userRequestDto = modelMapper.map(user, UserResponse.class);
        assertEquals(user.getFirstName(), userRequestDto.getFirstName());
        assertEquals(user.getEmail(), userRequestDto.getEmail());
    }
 }
