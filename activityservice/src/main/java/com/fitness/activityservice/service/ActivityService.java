package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.module.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityResponse trackActivity(ActivityRequest request) {
        Activity newActivity = convertToEntity(request);
        Activity savedActivity = activityRepository.save(newActivity);
        return convertToResponse(savedActivity);
    }

    private ActivityResponse convertToResponse(Activity savedActivity) {
        return ActivityResponse.builder()
                .Id(savedActivity.getId())
                .userId(savedActivity.getUserId())
                .additionalMetrics(savedActivity.getAdditionalMetrics())
                .createdAt(savedActivity.getCreatedAt())
                .caloriesBurned(savedActivity.getCaloriesBurned())
                .duration(savedActivity.getDuration())
                .startTime(savedActivity.getStartTime())
                .updatedAt(savedActivity.getUpdatedAt())
                .type(savedActivity.getType())
                .build();
    }

    private Activity convertToEntity(ActivityRequest request) {
        return Activity.builder()
                .userId(request.getUserId())
                .caloriesBurned(request.getCaloriesBurned())
                .type(request.getType())
                .additionalMetrics(request.getAdditionalMetrics())
                .startTime(request.getStartTime())
                .duration(request.getDuration())
                .build();
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activityList = activityRepository.findByUserId(userId);
        List<ActivityResponse> activityResponseList = activityList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return activityResponseList;
    }

    public ActivityResponse getActivity(String activityId) {
        return activityRepository.findById(activityId)
                .map(this::convertToResponse)
                .orElseThrow(()-> new RuntimeException("Activity not found with id: "+ activityId));
    }
}
