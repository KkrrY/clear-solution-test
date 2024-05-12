package com.example.clearsolutiontest.service.impl;

import com.example.clearsolutiontest.domain.User;
import com.example.clearsolutiontest.exception.ApiRequestException;
import com.example.clearsolutiontest.exception.EmailException;
import com.example.clearsolutiontest.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static com.example.clearsolutiontest.util.TestConstants.*;
import static com.example.clearsolutiontest.constants.ErrorMessage.*;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findUsersInRange() {
        LocalDate from = LocalDate.of(1950, 1, 1);
        LocalDate to = LocalDate.of(2004, 1, 1);

        List<User> userList = new ArrayList<>();

        userList.add(new User());
        userList.add(new User());

        List<User> resultList = userService.searchUsersInRange(from, to);

        when(userRepository.findByBirthDateBetween(from, to)).thenReturn(resultList);

        assertEquals(2, userList.size());
        verify(userRepository, times(1)).findByBirthDateBetween(from, to);
    }

    @Test
    public void loadUserByUsername() {
        User user = new User();
        user.setEmail(USER_EMAIL);
        user.setFirstName(FIRST_NAME);

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        assertEquals(USER_EMAIL, user.getEmail());
        assertEquals(FIRST_NAME, user.getFirstName());
    }

    @Test
    public void updateUserInfo() {
        User user = new User();
        user.setEmail(USER_EMAIL);
        user.setFirstName(FIRST_NAME);

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        userService.updateUserInfo(USER_EMAIL, user);
        assertEquals(USER_EMAIL, user.getEmail());
        assertEquals(FIRST_NAME, user.getFirstName());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    public void deleteUser () {
        User user = new User();
        user.setEmail(USER_EMAIL);

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));

        String result = userService.deleteUser(user);

        verify(userRepository, times(1)).delete(user);

        assertEquals(USER_DELETED_SUCCESS, result);
    }

    @Test
    public void deleteUser_UserDoesNotExist_ExceptionThrown() {
        User user = new User();
        user.setEmail(USER_EMAIL);

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> userService.deleteUser(user));

        verify(userRepository, never()).delete(user);
    }

    @Test
    public void registerUser_Success() {
        User user = new User();
        user.setEmail(USER_EMAIL);

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.empty());

        String result = userService.registerUser(user);

        verify(userRepository, times(1)).save(user);

        assertEquals(USER_REGISTERED_SUCCESS, result);
    }

    @Test
    public void registerUser_EmailInUse_ExceptionThrown() {
        User existingUser = new User();
        existingUser.setEmail(USER_EMAIL);
        
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(existingUser));
        
        User newUser = new User();
        newUser.setEmail(USER_EMAIL);
        
        assertThrows(EmailException.class, () -> userService.registerUser(newUser));
        
        verify(userRepository, never()).save(newUser);
    }
}
