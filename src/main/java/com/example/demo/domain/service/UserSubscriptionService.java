package com.example.demo.domain.service;

import com.example.demo.domain.entity.UserSubscription;
import com.example.demo.domain.value.dto.ResourceDTO;
import com.example.demo.domain.value.dto.SignUpDTO;
import com.example.demo.domain.value.dto.UserDTO;
import com.example.demo.domain.value.dto.UserSubscriptionDTO;
import com.example.demo.domain.value.dto.update.UpdateUserDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface UserSubscriptionService {

    Page<UserSubscriptionDTO> getAllUserSubscriptions(Map<String,Object> filterOption);
    List<UserSubscriptionDTO> getAllResoucesByCourseId(Long courseId);
    Long createResource(UserSubscriptionDTO dto);
    Long deleteResource(Long id);
    Long updateResource(UserSubscriptionDTO dto, Long id);
    UserSubscriptionDTO getResourceByTitle(String title);
}
