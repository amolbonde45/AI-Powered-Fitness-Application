package com.AI_Powered_Fitness_App.UserService.Service;

import com.AI_Powered_Fitness_App.UserService.DTO.RegisterRequest;
import com.AI_Powered_Fitness_App.UserService.DTO.UserResponse;
import com.AI_Powered_Fitness_App.UserService.Repository.UserRepository;
import com.AI_Powered_Fitness_App.UserService.model.User;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public UserResponse register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){

            User existingUser = userRepository.findByEmail(request.getEmail());

            UserResponse userResponse = new UserResponse();
            userResponse.setId(existingUser.getId());
            userResponse.setKeyCloakId(existingUser.getKeyCloakId());
            userResponse.setEmail(existingUser.getEmail());
            userResponse.setPassword(existingUser.getPassword());
            userResponse.setFirstName(existingUser.getFirstName());
            userResponse.setLastName(existingUser.getLastName());
            userResponse.setCreatedAt(existingUser.getCreatedAt());
            userResponse.setUpdatedAt(existingUser.getUpdatedAt());
            return userResponse;
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser = userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setKeyCloakId(savedUser.getKeyCloakId());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());

        return userResponse;

    }

    public UserResponse getUserProfile(String userId) {

        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User Not Exist"));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());

        return userResponse;

    }

    public Boolean existByUserId(String userId) {

        return userRepository.existsByKeyCloakId(userId);
    }
}
