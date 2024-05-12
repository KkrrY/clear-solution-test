package com.example.clearsolutiontest.controller;

import com.example.clearsolutiontest.dto.RegistrationRequest;
import com.example.clearsolutiontest.dto.user.UpdateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static com.example.clearsolutiontest.constants.PathConstants.*;
import static com.example.clearsolutiontest.util.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static com.example.clearsolutiontest.constants.ErrorMessage.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/sql/create-user-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-user-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getUserInfoByEmail_Success() throws Exception {

        mockMvc.perform(get(API_V1_USERS + USER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("email", USER_EMAIL)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                ;
    }

    @Test
    public void getUserInRange_Success() throws Exception {

        mockMvc.perform(get(API_V1_USERS + DATE_RANGE)
                        .param("from", String.valueOf(BIRTH_DATE))
                        .param("to", "2022-12-31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Alice"));
    }

    @Test
    public void registration_Success() throws Exception {

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmail("email@example.com");
        registrationRequest.setFirstName("Name");
        registrationRequest.setLastName("Last");
        registrationRequest.setBirthDate(LocalDate.of(2000, 1, 1));

        mockMvc.perform(post(API_V1_USERS + REGISTRATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationRequest))  )
                .andExpect(status().isOk())
                .andExpect(content().json(String.valueOf(mapper.createObjectNode().put("message",USER_REGISTERED_SUCCESS))));
    }

    @Test
    public void registration_ShouldInputFieldsAreEmpty() throws Exception {
        mockMvc.perform(post(API_V1_USERS + REGISTRATION)
                        .content(mapper.writeValueAsString(new RegistrationRequest()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUserInfo_Success() throws Exception {

        UpdateUserRequest userRequest = new UpdateUserRequest();
        userRequest.setEmail(USER2_EMAIL);
        userRequest.setFirstName("UpdatedFirstNames");
        userRequest.setLastName("UpdatedLastName");
        userRequest.setBirthDate( LocalDate.of(2003, 1, 1) );


        mockMvc.perform(MockMvcRequestBuilders.put(API_V1_USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userRequest))
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(11))
                .andExpect(jsonPath("$.email").value("alice@example.com"))
                .andExpect(jsonPath("$.firstName").value("UpdatedFirstNames"))
                .andExpect(jsonPath("$.lastName").value("UpdatedLastName"))
                .andExpect(jsonPath("$.birthDate").value("2003-01-01"));
    }

    @Test
    public void deleteUser_Success() throws Exception {

        mockMvc.perform(delete(API_V1_USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USER2_EMAIL))
                .andExpect(status().isOk())
                .andExpect(content().json(String.valueOf(mapper.createObjectNode().put("message",USER_DELETED_SUCCESS))));
    }


}
