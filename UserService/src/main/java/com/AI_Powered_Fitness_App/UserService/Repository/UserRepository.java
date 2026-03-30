package com.AI_Powered_Fitness_App.UserService.Repository;

import com.AI_Powered_Fitness_App.UserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String > {

    boolean existsByEmail(String email);
}
