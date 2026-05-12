package com.AI_Powered_Fitness_App.AIService.modle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String activityId;
    private String userId;
    @Column(columnDefinition = "TEXT")
    private String activityType;

    @Column(columnDefinition = "TEXT")
    private String recommendations;
    @ElementCollection
    private List<String> improvement;
    @ElementCollection
    private List<String> suggestions;
    @ElementCollection
    private List<String> safety;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
