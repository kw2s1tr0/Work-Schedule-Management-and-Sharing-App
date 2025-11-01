package com.schedule.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schedule.app.dto.response.ScheduleListDTO;
import com.schedule.app.form.ScheduleSearchForm;

@RestController
public class ScheduleController {
    
    @GetMapping("/schedule")
    public ScheduleListDTO getSchedule(ScheduleSearchForm form) {
        return new ScheduleListDTO();
    }
}
