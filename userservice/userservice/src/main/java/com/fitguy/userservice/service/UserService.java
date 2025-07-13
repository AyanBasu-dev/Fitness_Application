package com.fitguy.userservice.service;

import com.fitguy.userservice.dto.RegisterRequest;
import com.fitguy.userservice.dto.UserResponse;
import com.fitguy.userservice.model.User;
import com.fitguy.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUserProfile(String userId) {
        return convertUserToUserResponse(userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User does not exist")));
    }

    public UserResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        User newUser = convertRegisterRequestToUser(request);
        User savedUser = userRepository.save(newUser);
        return convertUserToUserResponse(savedUser);
    }

    private UserResponse convertUserToUserResponse(User savedUser) {
        return UserResponse.builder()
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();
    }

    private User convertRegisterRequestToUser(RegisterRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }
}
