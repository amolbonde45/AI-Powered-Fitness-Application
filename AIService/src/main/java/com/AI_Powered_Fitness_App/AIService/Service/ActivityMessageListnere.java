package com.AI_Powered_Fitness_App.AIService.Service;

import com.AI_Powered_Fitness_App.AIService.modle.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class ActivityMessageListnere {



    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity){
        log.info("Received Activity for processing : {} ", activity.getId());
    }


}
