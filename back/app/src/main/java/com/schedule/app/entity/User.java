package com.schedule.app.entity;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String userName;
    private String organizationName;
    private List<Schedule> schedules;
}
