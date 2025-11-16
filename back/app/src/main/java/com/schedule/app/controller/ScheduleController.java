package com.schedule.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schedule.app.applicationservice.DeleteDefaultScheduleService;
import com.schedule.app.applicationservice.DeleteIrregularScheduleService;
import com.schedule.app.applicationservice.DeleteRegularScheduleService;
import com.schedule.app.applicationservice.GetDefaultScheduleService;
import com.schedule.app.applicationservice.GetIrregularScheduleService;
import com.schedule.app.applicationservice.GetRegularScheduleService;
import com.schedule.app.dto.DefaultScheduleDTO;
import com.schedule.app.dto.IrregularScheduleDTO;
import com.schedule.app.dto.RegularScheduleDTO;
import com.schedule.app.dto.UserDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.form.DefaultScheduleForm;
import com.schedule.app.form.IrregularScheduleForm;
import com.schedule.app.form.RegularScheduleForm;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.applicationservice.GetScheduleService;
import com.schedule.app.applicationservice.PatchDefaultScheduleService;
import com.schedule.app.applicationservice.PatchIrregularScheduleService;
import com.schedule.app.applicationservice.PatchRegularScheduleService;
import com.schedule.app.applicationservice.PostDefaultScheduleService;
import com.schedule.app.applicationservice.PostIrregularScheduleService;
import com.schedule.app.applicationservice.PostRegularScheduleService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ScheduleController {

    private final GetScheduleService scheduleService;
    private final GetIrregularScheduleService irregularScheduleService;
    private final GetRegularScheduleService regularScheduleService;
    private final GetDefaultScheduleService defaultScheduleService;
    private final PostIrregularScheduleService postIrregularScheduleService;
    private final PostRegularScheduleService postRegularScheduleService;
    private final PostDefaultScheduleService postDefaultScheduleService;
    private final PatchIrregularScheduleService patchIrregularScheduleService;
    private final PatchRegularScheduleService patchRegularScheduleService;
    private final PatchDefaultScheduleService patchDefaultScheduleService;
    private final DeleteIrregularScheduleService deleteIrregularScheduleService;
    private final DeleteRegularScheduleService deleteRegularScheduleService;    
    private final DeleteDefaultScheduleService deleteDefaultScheduleService;

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

    @PostMapping("/api/irregularSchedule")
    public void postIrregularSchedule(@Valid IrregularScheduleForm form) {
        postIrregularScheduleService.postIrregularScheduleService(form);
    }

    @PostMapping("/api/regularSchedule")
    public void postRegularSchedule(@Valid RegularScheduleForm form) {
        postRegularScheduleService.postRegularScheduleService(form);
    }

    @PostMapping("/api/defaultSchedule")
    public void postDefaultSchedule(@Valid DefaultScheduleForm form) {
        postDefaultScheduleService.postDefaultScheduleService(form);
    }

    @PatchMapping("/api/irregularSchedule")
    public void patchIrregularSchedule(@Valid IrregularScheduleForm form) {
        patchIrregularScheduleService.patchIrregularScheduleService(form);
    }

    @PatchMapping("/api/regularSchedule")
    public void patchRegularSchedule(@Valid RegularScheduleForm form) {
        patchRegularScheduleService.patchRegularScheduleService(form);
    }

    @PatchMapping("/api/defaultSchedule")
    public void patchDefaultSchedule(@Valid DefaultScheduleForm form) {
        patchDefaultScheduleService.patchDefaultScheduleService(form);
    }

    @DeleteMapping("/api/irregularSchedule/{id}")
    public void deleteIrregularSchedule(@PathVariable Integer id) {
        deleteIrregularScheduleService.deleteIrregularScheduleService(id);
    }

    @DeleteMapping("/api/regularSchedule/{id}")
    public void deleteRegularSchedule(@PathVariable Integer id) {
        deleteRegularScheduleService.deleteRegularScheduleService(id);
    }

    @DeleteMapping("/api/defaultSchedule/{id}")
    public void deleteDefaultSchedule(@PathVariable Integer id) {
        deleteDefaultScheduleService.deleteDefaultScheduleService(id);
    }
}