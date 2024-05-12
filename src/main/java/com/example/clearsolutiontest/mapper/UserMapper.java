package com.example.clearsolutiontest.mapper;

import com.example.clearsolutiontest.domain.User;
import com.example.clearsolutiontest.dto.RegistrationRequest;
import com.example.clearsolutiontest.dto.user.UpdateUserRequest;
import com.example.clearsolutiontest.dto.user.UserResponse;
import com.example.clearsolutiontest.exception.InputFieldException;
import com.example.clearsolutiontest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final CommonMapper commonMapper;
    private final UserService userService;

    public UserResponse findUserByEmail(String email) {
        return commonMapper.convertToResponse(userService.findByEmail(email), UserResponse.class);
    }

    public String registerUser (RegistrationRequest registrationRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            throw new InputFieldException(bindingResult);
        }

        User user = commonMapper.convertToEntity(registrationRequest, User.class);
        return userService.registerUser(user);
    }

    public UserResponse updateUserInfo(String email, UpdateUserRequest userRequest, BindingResult bindingResult ) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }

        User user = commonMapper.convertToEntity(userRequest, User.class);
        return commonMapper.convertToResponse(userService.updateUserInfo(email, user), UserResponse.class);
    }

    public String deleteUser(String email) {
        User user = userService.findByEmail(email);
        return userService.deleteUser(user);
    }

    public List<UserResponse> searchUsersInRange(LocalDate from, LocalDate to) {
        List<User> rangedUsers = userService.searchUsersInRange(from, to);

         return commonMapper.convertToResponseList(rangedUsers, UserResponse.class);
    }

}
