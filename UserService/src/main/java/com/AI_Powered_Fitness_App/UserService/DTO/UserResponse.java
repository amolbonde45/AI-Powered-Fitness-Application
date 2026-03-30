package com.AI_Powered_Fitness_App.UserService.DTO;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {

    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
