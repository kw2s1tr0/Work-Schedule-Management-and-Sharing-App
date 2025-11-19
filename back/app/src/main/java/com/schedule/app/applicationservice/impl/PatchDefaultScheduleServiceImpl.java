package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PatchDefaultScheduleService;
import com.schedule.app.domainservice.DefaultScheduleService;
import com.schedule.app.form.DefaultScheduleForm;
import com.schedule.app.record.input.DefaultScheduleInputRecord;
import com.schedule.app.repository.ScheduleUpdateMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatchDefaultScheduleServiceImpl implements PatchDefaultScheduleService{
    private final ScheduleUpdateMapper scheduleUpdateMapper;
    private final DefaultScheduleService defaultScheduleService;

    /**
     * デフォルトスケジュールを更新する
     * 
     * @param form 画面入力フォーム
     * @param userId ユーザーID
     */
    @Override
    public void patchDefaultScheduleService(DefaultScheduleForm form, String userId){
        DefaultScheduleInputRecord record = toDefaultScheduleRecord(form,userId);
        patchDefaultSchedule(record);
    }

    /**
     * フォームをレコードに変換する
     * 
     * @param form 画面入力フォーム
     * @param userId ユーザーID 
     * @return デフォルトスケジュール入力レコード
     */
    @Override
    public DefaultScheduleInputRecord toDefaultScheduleRecord(DefaultScheduleForm form, String userId){

        // デフォルトスケジュールの存在チェック
        defaultScheduleService.existDefaultSchedule(form.id(), userId);
        
        // デフォルトスケジュールの重複チェック
        defaultScheduleService.checkDefaultSchedule(form.id(), userId, form.startDate(), form.endDate());

        DefaultScheduleInputRecord record = DefaultScheduleInputRecord.builder()
                                                .id(form.id())
                                                .userId(userId)
                                                .startTime(form.startTime())
                                                .endTime(form.endTime())
                                                .startDate(form.startDate())
                                                .endDate(form.endDate())
                                                .workTypeId(form.workTypeId())
                                                .build();

        return record;
    }

    /**
     * デフォルトスケジュールを更新する
     * 
     * @param record デフォルトスケジュール入力レコード
     */
    @Override
    public void patchDefaultSchedule(DefaultScheduleInputRecord record) {
        scheduleUpdateMapper.updateDefaultSchedule(record);
    }
}
