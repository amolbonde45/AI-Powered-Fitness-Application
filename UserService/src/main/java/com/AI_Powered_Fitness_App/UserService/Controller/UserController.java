package com.AI_Powered_Fitness_App.UserService.Controller;

import com.AI_Powered_Fitness_App.UserService.DTO.RegisterRequest;
import com.AI_Powered_Fitness_App.UserService.DTO.UserResponse;
import com.AI_Powered_Fitness_App.UserService.Service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {


    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(
            @PathVariable String userId
    ){
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
           @Valid @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUser(
            @PathVariable String userId
    ){
        return ResponseEntity.ok(userService.existByUserId(userId));
    }


}
