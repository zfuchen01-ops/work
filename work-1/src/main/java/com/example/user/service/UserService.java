package com.example.user.service;

import com.example.user.domain.User;
import com.example.user.dto.AuthResponse;
import com.example.user.dto.LoginRequest;
import com.example.user.dto.RegisterRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    Optional<User> findById(Long id);

    List<User> findAll();

    User update(Long id, User updated);

    void delete(Long id);
}
