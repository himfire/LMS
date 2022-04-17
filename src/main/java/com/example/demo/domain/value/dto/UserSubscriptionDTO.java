package com.example.demo.domain.value.dto;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.value.enumurator.Status;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSubscriptionDTO {

    private User subscribedUser;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private Long rating;
    private String comment;
}
