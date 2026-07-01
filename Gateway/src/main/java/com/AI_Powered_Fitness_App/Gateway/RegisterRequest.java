package com.AI_Powered_Fitness_App.Gateway;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    private String keyCloakID;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min = 6, message = "Password should have atleast 6 Characters")
    private String password;


    private String firstName;
    private String lastName;

}
