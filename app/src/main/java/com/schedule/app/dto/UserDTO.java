package com.schedule.app.dto;

import java.util.List;

import com.schedule.app.dto.item.ScheduleDTO;

import lombok.Builder;

@Builder
public record UserDTO (
    int userId,
    String userName,
    String organizationName,
    List<ScheduleDTO> schedules
){}
