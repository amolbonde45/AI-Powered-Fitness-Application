package com.AI_Powered_Fitness_App.AIService.Service;

import com.AI_Powered_Fitness_App.AIService.Repository.RecommendationRepository;
import com.AI_Powered_Fitness_App.AIService.modle.Activity;
import com.AI_Powered_Fitness_App.AIService.modle.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class ActivityMessageListnere {

    private final ActivityAIService activityAIService;
    private final RecommendationRepository recommendationRepository;


    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processActivity(Activity activity) {
        try {
            log.info("Received Activity for processing : {}", activity.getId());

            Recommendation recommendation = activityAIService.generateRecommendation(activity);

            if (recommendation == null) {
                log.warn("Recommendation not generated for activityId={}", activity.getId());
                return;
            }

            recommendationRepository.save(recommendation);

            log.info("Recommendation saved successfully for activityId={}", activity.getId());

        } catch (Exception e) {
            log.error("Failed to process activityId={}. Reason: {}", activity.getId(), e.getMessage());
        }
    }


}
