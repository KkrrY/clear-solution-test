package com.example.clearsolutiontest.repository;

import com.example.clearsolutiontest.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByBirthDateBetween(LocalDate from, LocalDate to);
}
