package com.AI_Powered_Fitness_App.ActivityService.DTO;

import com.AI_Powered_Fitness_App.ActivityService.Model.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityRequest {

    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String , Object> additionalMatrics;
}
