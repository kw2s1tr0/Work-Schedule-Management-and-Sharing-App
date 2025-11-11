package com.schedule.app.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record UserDTO(
        String userName,
        String organizationName,
        List<ScheduleDTO> schedules) {
}
