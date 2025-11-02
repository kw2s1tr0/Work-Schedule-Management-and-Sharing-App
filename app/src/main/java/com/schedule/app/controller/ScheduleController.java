package com.schedule.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schedule.app.dto.UserDTO;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.service.ScheduleService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    
    @GetMapping("/schedule")
    public List<UserDTO> getSchedule(ScheduleSearchForm form) {
        List<UserDTO> userDTOs = scheduleService.scheduleSearchService(form);
        return userDTOs;
    }
}
