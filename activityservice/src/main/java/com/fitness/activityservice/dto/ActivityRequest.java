package com.fitness.activityservice.dto;

import com.fitness.activityservice.module.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityRequest {
    private String userId;
    private ActivityType type;
    private LocalDateTime startTime;
    private Integer duration;
    private Integer caloriesBurned;
    private Map<String, Object> additionalMetrics;

}
