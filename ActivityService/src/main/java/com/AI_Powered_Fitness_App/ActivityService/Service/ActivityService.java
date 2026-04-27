package com.AI_Powered_Fitness_App.ActivityService.Service;

import com.AI_Powered_Fitness_App.ActivityService.ActivityRepository;
import com.AI_Powered_Fitness_App.ActivityService.DTO.ActivityRequest;
import com.AI_Powered_Fitness_App.ActivityService.DTO.ActivityResponse;
import com.AI_Powered_Fitness_App.ActivityService.Model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ActivityService {

    private  final ActivityRepository activityRepository;
    private final UserValidateService userValidateService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private  String exchange;
    @Value("${rabbitmq.routing.key}")
    private  String routingKey;

    public ActivityResponse trackActivity(
            ActivityRequest request
    ) {

        boolean isValidUser = userValidateService.validateUser( request.getUserId() );

        if(!isValidUser){
            throw new RuntimeException( "User is not valid " + request.getUserId() );
        }
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMatrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        try {
            rabbitTemplate.convertAndSend( exchange , routingKey , savedActivity);
        } catch (Exception e) {
            log.error("Failed to publish Activity to RabbitMq : ", e);
        }

        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(
            Activity activity
    ){
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());;
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }

    public List<ActivityResponse> getUserActivity(String userId) {

       List<Activity> activities =  activityRepository.findAllByUserId(userId);
       return activities.stream()
               .map(this::mapToResponse)
               .collect(Collectors.toList());

    }

    public  ActivityResponse getUserActivityById(String id) {

        return activityRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(()-> new RuntimeException("Activity Not Found by ID " + id));

    }
}
