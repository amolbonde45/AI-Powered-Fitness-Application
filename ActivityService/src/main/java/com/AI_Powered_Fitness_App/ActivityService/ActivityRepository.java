package com.AI_Powered_Fitness_App.ActivityService;

import com.AI_Powered_Fitness_App.ActivityService.Model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {

    List<Activity> findAllByUserId(String userId);
}
