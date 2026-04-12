package com.AI_Powered_Fitness_App.ActivityService.Controller;

import com.AI_Powered_Fitness_App.ActivityService.DTO.ActivityRequest;
import com.AI_Powered_Fitness_App.ActivityService.DTO.ActivityResponse;
import com.AI_Powered_Fitness_App.ActivityService.Service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping("/activitys")
    public ResponseEntity<ActivityResponse> trackActivity(
        @RequestBody ActivityRequest request
    ){
        return ResponseEntity.ok(activityService.trackActivity(request));
    }

    @GetMapping("/getActivities")
    public ResponseEntity<List<ActivityResponse>> getUserActivity(
            @RequestHeader("X-User-ID") String userId
    ){
        return ResponseEntity.ok(activityService.getUserActivity(userId));
    }

    @GetMapping("/getActivities/{id}")
    public ResponseEntity<ActivityResponse> getUserActivityById(
            @PathVariable String id
    ){
        return ResponseEntity.ok(activityService.getUserActivityById(id));
    }

}
