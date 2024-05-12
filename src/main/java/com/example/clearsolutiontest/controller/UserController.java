package com.example.clearsolutiontest.controller;

import com.example.clearsolutiontest.dto.RegistrationRequest;
import com.example.clearsolutiontest.dto.user.UpdateUserRequest;
import com.example.clearsolutiontest.dto.user.UserResponse;
import com.example.clearsolutiontest.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.example.clearsolutiontest.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_USERS)
public class UserController {
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    @GetMapping(USER)
    public ResponseEntity<UserResponse> getUserByEmail (@RequestParam String email) {
        return ResponseEntity.ok(userMapper.findUserByEmail(email));

    }

    @GetMapping(DATE_RANGE)
    public ResponseEntity<List<UserResponse>> getUserInRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to){
        return ResponseEntity.ok(userMapper.searchUsersInRange(from, to));
    }

    @PostMapping(REGISTRATION)
    public ResponseEntity<ObjectNode> registration(@Valid @RequestBody RegistrationRequest user, BindingResult bindingResult) {
        return ResponseEntity.ok(
                objectMapper.createObjectNode().put("message", userMapper.registerUser(user, bindingResult))
        );
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUserInfo(@Valid @RequestBody UpdateUserRequest userRequest, BindingResult bindingResult){
        return ResponseEntity.ok(userMapper.updateUserInfo(userRequest.getEmail(), userRequest, bindingResult));
    }

    @DeleteMapping
    public ResponseEntity<ObjectNode> deleteUser(@RequestBody String email) {
        return ResponseEntity.ok(
                objectMapper.createObjectNode().put("message", userMapper.deleteUser(email))
        );
    }
}
