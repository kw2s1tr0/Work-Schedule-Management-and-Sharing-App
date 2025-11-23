package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PostDefaultScheduleService;
import com.schedule.app.domainservice.DefaultScheduleService;
import com.schedule.app.form.DefaultScheduleInsertForm;
import com.schedule.app.record.input.DefaultScheduleInputRecord;
import com.schedule.app.repository.ScheduleCreateMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostDefaultScheduleServiceImpl implements PostDefaultScheduleService{
    private final ScheduleCreateMapper scheduleCreateMapper;
    private final DefaultScheduleService defaultScheduleService;

    /**
     * デフォルトスケジュールを登録する
     * 
     * @param form 画面入力フォーム 
     * @param userId ユーザーID
     */
    @Override
    public void postDefaultScheduleService(DefaultScheduleInsertForm form, String userId){
        DefaultScheduleInputRecord record = toDefaultScheduleRecord(form, userId);
        postDefaultSchedule(record);
    }

    /**
     * フォームをエンティティに変換する
     * 
     * @param form 画面入力フォーム
     * @param userId ユーザーID 
     * @return デフォルトスケジュールエンティティ
     */
    @Override
    public DefaultScheduleInputRecord toDefaultScheduleRecord(DefaultScheduleInsertForm form, String userId){


        // デフォルトスケジュールの重複チェック
        defaultScheduleService.checkDefaultSchedule(null,userId, form.startDate(), form.endDate());

        DefaultScheduleInputRecord record = DefaultScheduleInputRecord.builder()
                                                .userId(userId) //ログイン機能を使用するか仮に
                                                .startTime(form.startTime())
                                                .endTime(form.endTime())
                                                .startDate(form.startDate())
                                                .endDate(form.endDate())
                                                .workTypeId(form.workTypeId())
                                                .build();

        return record;
    }

    /**
     * デフォルトスケジュールを登録する
     * 
     * @param record デフォルトスケジュール入力レコード
     */
    @Override
    public void postDefaultSchedule(DefaultScheduleInputRecord record) {
        scheduleCreateMapper.createDefaultSchedule(record);
    }
   
}
