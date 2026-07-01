package com.AI_Powered_Fitness_App.Gateway.user;

import com.AI_Powered_Fitness_App.Gateway.RegisterRequest;
import com.AI_Powered_Fitness_App.Gateway.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final WebClient userServiceWebClient;

    public Mono<Boolean> validateUser(String userId){

        log.info("Calling User Validation api for userId: {} " + userId);
            return userServiceWebClient
                    .get()
                    .uri("/api/user/{userid}/validate" , userId)
                    .retrieve()
                    .bodyToMono(boolean.class)
                    .onErrorResume(WebClientResponseException.class, e->{
                        if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                            return Mono.error(new RuntimeException("User Not Found" + userId));
                        else if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                            return Mono.error(new RuntimeException("Invalid Request" + userId));
                        return Mono.error(new RuntimeException("Unexpected Error" + e.getMessage()));


                    });

    }

    public Mono<UserResponse> registerUser(RegisterRequest request) {

        log.info("Calling User Registration api for email: {} " + request.getEmail());
        return userServiceWebClient
                .post()
                .uri("/api/user/register")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class, e->{
                    if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                        return Mono.error(new RuntimeException("bad Request" + e.getMessage()));
                    else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
                        return Mono.error(new RuntimeException("INTERNAL_SERVER_ERROR" + e.getMessage()));
                    return Mono.error(new RuntimeException("Unexpected Error" + e.getMessage()));


                });

    }
}
