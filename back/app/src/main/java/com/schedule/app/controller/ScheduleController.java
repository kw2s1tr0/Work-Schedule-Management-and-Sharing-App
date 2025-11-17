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
import com.schedule.app.applicationservice.GetOrganizationService;
import com.schedule.app.applicationservice.GetWorkTypeService;
import com.schedule.app.dto.DefaultScheduleDTO;
import com.schedule.app.dto.IrregularScheduleDTO;
import com.schedule.app.dto.OrganizationDTO;
import com.schedule.app.dto.RegularScheduleDTO;
import com.schedule.app.dto.UserDTO;
import com.schedule.app.dto.WorkTypeDTO;
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
    private final GetOrganizationService organizationService;
    private final GetWorkTypeService workTypeService;

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

    /**
     * イレギュラースケジュール取得
     * 
     * @param form 画面入力フォーム
     * @return イレギュラースケジュールDTOリスト
     */
    @GetMapping("/api/irregularSchedule")
    public List<IrregularScheduleDTO> getIrregularSchedule(@Valid SingleScheduleSearchForm form) {
        String userId = "00001"; // 仮のユーザーID、実際には認証情報などから取得する
        List<IrregularScheduleDTO> userDTOs = irregularScheduleService.irregularScheduleSearchService(form, userId);
        return userDTOs;
    }

        /**
        * レギュラースケジュール取得
        * 
        * @param form 画面入力フォーム
        * @return レギュラースケジュールDTOリスト
        */
    @GetMapping("/api/regularSchedule")
    public List<RegularScheduleDTO> getRegularSchedule(@Valid SingleScheduleSearchForm form) {
        String userId = "00001"; // 仮のユーザーID、実際には認証情報などから取得する
        List<RegularScheduleDTO> regularSchedules = regularScheduleService.regularScheduleSearchService(form, userId);
        return regularSchedules;
    }

    /**
     * デフォルトスケジュール取得
     * 
     * @param form 画面入力フォーム
     * @return デフォルトスケジュールDTOリスト
     */
    @GetMapping("/api/defaultSchedule")
    public List<DefaultScheduleDTO> getDefaultSchedule(@Valid SingleScheduleSearchForm form) {
        String userId = "00001"; // 仮のユーザーID、実際には認証情報などから取得する
        List<DefaultScheduleDTO> defaultSchedules = defaultScheduleService.defaultScheduleSearchService(form, userId);
        return defaultSchedules;
    }

    /**
     * イレギュラースケジュール登録
     * 
     * @param form 画面入力フォーム
     */
    @PostMapping("/api/irregularSchedule")
    public void postIrregularSchedule(@Valid IrregularScheduleForm form) {
        String userId = "00001"; // 仮のユーザーID、実際には認証情報などから取得する
        postIrregularScheduleService.postIrregularScheduleService(form, userId);
    }

        /**
        * レギュラースケジュール登録
        * 
        * @param form 画面入力フォーム
        */
    @PostMapping("/api/regularSchedule")
    public void postRegularSchedule(@Valid RegularScheduleForm form) {
        String userId = "00001"; // 仮のユーザーID、実際には認証情報などから取得する
        postRegularScheduleService.postRegularScheduleService(form, userId);
    }

    /**
     * デフォルトスケジュール登録
     * 
     * @param form 画面入力フォーム
     */
    @PostMapping("/api/defaultSchedule")
    public void postDefaultSchedule(@Valid DefaultScheduleForm form) {
        String userId = "00001"; // 仮のユーザーID、実際には認証情報などから取得する
        postDefaultScheduleService.postDefaultScheduleService(form, userId);
    }

    /**
     * イレギュラースケジュール更新
     * 
     * @param form 画面入力フォーム
     */
    @PatchMapping("/api/irregularSchedule")
    public void patchIrregularSchedule(@Valid IrregularScheduleForm form) {
        String userId = "00001"; // 仮のユーザーID、実際には認証情報などから取得する
        patchIrregularScheduleService.patchIrregularScheduleService(form, userId);
    }

    /**
     * レギュラースケジュール更新
     * 
     * @param form 画面入力フォーム
     */
    @PatchMapping("/api/regularSchedule")
    public void patchRegularSchedule(@Valid RegularScheduleForm form) {
        String userId = "00001"; // 仮のユーザーID、実際には認証情報などから取得する
        patchRegularScheduleService.patchRegularScheduleService(form, userId);
    }

    /**
     * デフォルトスケジュール更新
     * 
     * @param form 画面入力フォーム
     */
    @PatchMapping("/api/defaultSchedule")
    public void patchDefaultSchedule(@Valid DefaultScheduleForm form) {
        String userId = "00001"; // 仮のユーザーID、実際には認証情報などから取得する
        patchDefaultScheduleService.patchDefaultScheduleService(form, userId);
    }

    /**
     * イレギュラースケジュール削除
     * 
     * @param id スケジュールID
     */
    @DeleteMapping("/api/irregularSchedule/{id}")
    public void deleteIrregularSchedule(@PathVariable Integer id) {
        String userId = "00001"; // 仮のユーザーID、実際には認証情報などから取得する
        deleteIrregularScheduleService.deleteIrregularScheduleService(id, userId);
    }

    /**
     * レギュラースケジュール削除
     * 
     * @param id スケジュールID
     */
    @DeleteMapping("/api/regularSchedule/{id}")
    public void deleteRegularSchedule(@PathVariable Integer id) {
        String userId = "00001"; // 仮のユーザーID、実際には認証情報などから取得する
        deleteRegularScheduleService.deleteRegularScheduleService(id, userId);
    }

    /**
     * デフォルトスケジュール削除
     * 
     * @param id スケジュールID
     */
    @DeleteMapping("/api/defaultSchedule/{id}")
    public void deleteDefaultSchedule(@PathVariable Integer id) {
        String userId = "00001"; // 仮のユーザーID、実際には認証情報などから取得する
        deleteDefaultScheduleService.deleteDefaultScheduleService(id, userId);
    }

    /**
     * 組織一覧取得
     * 全ての組織のコードと名称を取得する
     * 
     * @return 組織DTOリスト
     */
    @GetMapping("/api/organizations")
    public List<OrganizationDTO> getOrganizations() {
        return organizationService.getOrganizationList();
    }

    /**
     * 勤怠種別一覧取得
     * 全ての勤怠種別（休日を除く）のID、名称、カラーコードを取得する
     * 
     * @return 勤怠種別DTOリスト
     */
    @GetMapping("/api/workTypes")
    public List<WorkTypeDTO> getWorkTypes() {
        return workTypeService.getWorkTypeList();
    }
}