package com.AI_Powered_Fitness_App.Gateway;

import com.AI_Powered_Fitness_App.Gateway.user.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserSyncFilter implements WebFilter {

    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange , WebFilterChain chain){
        String userId = exchange.getRequest().getHeaders().getFirst("X-User_ID");
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        RegisterRequest request = getUserDetails(token);

        if(userId==null){
            userId=request.getKeyCloakID();
        }


        if(userId != null && token != null){
            String finalUserId = userId;
            return userService.validateUser(userId)
                    .flatMap(exist -> {
                        if(!exist){
                            //register user
                            if(request != null){
                                return userService.registerUser(request)
                                        .then(Mono.empty());
                            }else {
                                return Mono.empty();
                            }
                        }else {
                            log.info("User already Exist , Skipping sync.");
                            return Mono.empty();
                        }
                    })
                    .then(Mono.defer(()->{
                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("X-User-ID" , finalUserId)
                                .build();
                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    }));

        }
        return chain.filter(exchange);

    }

    private RegisterRequest getUserDetails(String token) {

        try {

            String tokenWithoutBarer = token.replace("Bearer","").trim();
            SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBarer);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setEmail(claims.getStringClaim("email"));
            registerRequest.setKeyCloakID(claims.getStringClaim("sub"));
            registerRequest.setPassword("dummy@123");
            registerRequest.setFirstName(claims.getStringClaim("given_name"));
            registerRequest.setLastName(claims.getStringClaim("family_name"));
            return registerRequest;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


}
