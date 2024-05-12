package com.example.clearsolutiontest.service;

import com.example.clearsolutiontest.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User findByEmail (String email);
    String registerUser (User user);
    User updateUserInfo(String email, User user);
    String deleteUser(User user);
    List<User> searchUsersInRange(LocalDate from, LocalDate to);
}
