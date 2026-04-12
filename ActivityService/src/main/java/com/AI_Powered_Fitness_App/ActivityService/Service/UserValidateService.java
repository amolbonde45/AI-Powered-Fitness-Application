package com.AI_Powered_Fitness_App.ActivityService.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserValidateService {

    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId){

        try {
            return userServiceWebClient
                    .get()
                    .uri("/api/user/{userid}/validate" , userId)
                    .retrieve()
                    .bodyToMono(boolean.class)
                    .block();

        }catch (WebClientResponseException ex){
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new RuntimeException("User Not Found " + userId);

        }

        return false;
    }

}
