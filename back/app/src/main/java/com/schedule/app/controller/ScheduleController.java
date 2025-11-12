package com.schedule.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schedule.app.applicationservice.getDefaultScheduleService;
import com.schedule.app.applicationservice.getIrregularScheduleService;
import com.schedule.app.applicationservice.getRegularScheduleService;
import com.schedule.app.dto.DefaultScheduleDTO;
import com.schedule.app.dto.IrregularScheduleDTO;
import com.schedule.app.dto.RegularScheduleDTO;
import com.schedule.app.dto.UserDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.applicationservice.getScheduleService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ScheduleController {

    private final getScheduleService scheduleService;
    private final getIrregularScheduleService irregularScheduleService;
    private final getRegularScheduleService regularScheduleService;
    private final getDefaultScheduleService defaultScheduleService;

    /**
     * スケジュール検索
     * ユーザーごとのスケジュールリストを検索条件に基づき取得する
     * 
     * @param form スケジュール検索条件
     * @return 条件に一致したユーザーDTOリスト(各ユーザーはスケジュールDTOリスト内包)
     */
    @GetMapping("/api/schedule")
    public List<UserDTO> getSchedule(@Valid ScheduleSearchForm form) {
        List<UserDTO> userDTOs = scheduleService.scheduleSearchService(form);
        return userDTOs;
    }

    @GetMapping("/api/irregularSchedule")
    public List<IrregularScheduleDTO> getIrregularSchedule(@Valid SingleScheduleSearchForm form) {
        List<IrregularScheduleDTO> userDTOs = irregularScheduleService.irregularScheduleSearchService(form);
        return userDTOs;
    }

    @GetMapping("/api/regularSchedule")
    public List<RegularScheduleDTO> getRegularSchedule(@Valid SingleScheduleSearchForm form) {
        List<RegularScheduleDTO> regularSchedules = regularScheduleService.regularScheduleSearchService(form);
        return regularSchedules;
    }

    @GetMapping("/api/defaultSchedule")
    public List<DefaultScheduleDTO> getDefaultSchedule(@Valid SingleScheduleSearchForm form) {
        List<DefaultScheduleDTO> defaultSchedules = defaultScheduleService.defaultScheduleSearchService(form);
        return defaultSchedules;
    }
}