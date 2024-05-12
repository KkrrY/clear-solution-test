package com.example.clearsolutiontest.service.impl;

import com.example.clearsolutiontest.domain.User;
import com.example.clearsolutiontest.exception.ApiRequestException;
import com.example.clearsolutiontest.exception.EmailException;
import com.example.clearsolutiontest.repository.UserRepository;
import com.example.clearsolutiontest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.clearsolutiontest.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));

    }

    @Override
    @Transactional
    public String registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent() ) {
            throw new EmailException(EMAIL_IN_USE);
        }

        userRepository.save(user);

        return USER_REGISTERED_SUCCESS ;
    }

    @Override
    @Transactional
    public User updateUserInfo(String email, User user) {
        User userFromDb = userRepository.findByEmail(email).orElse(null);
        if (userFromDb == null) {
            userFromDb = userRepository.findById(user.getId())
                    .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
        }

        userFromDb.setEmail(user.getEmail());
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setAddress(user.getAddress());
        userFromDb.setBirthDate(user.getBirthDate());
        userFromDb.setPhoneNumber(user.getPhoneNumber());

        return userFromDb;
    }

    @Override
    @Transactional
    public String deleteUser(User user) {

        if (!userRepository.findByEmail(user.getEmail()).isPresent() ) {
            throw new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        userRepository.delete(user);

        return USER_DELETED_SUCCESS ;
    }

    @Override
    public List<User> searchUsersInRange(LocalDate from, LocalDate to) {

        if (from.isAfter(to)) {
            throw new ApiRequestException(INVALID_DATE, HttpStatus.BAD_REQUEST);
        }

        List<User> users =  userRepository.findByBirthDateBetween(from, to);

        if (users.isEmpty()) {
            throw new ApiRequestException(NOT_FOUND_IN_RANGE, HttpStatus.NOT_FOUND);
        }

        return Optional.of(users)
                .orElseThrow(() -> new ApiRequestException(NOT_FOUND_IN_RANGE, HttpStatus.NOT_FOUND));
    }
}
