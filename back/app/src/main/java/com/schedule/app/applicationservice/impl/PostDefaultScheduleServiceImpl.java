package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PostDefaultScheduleService;
import com.schedule.app.domainservice.DefaultScheduleService;
import com.schedule.app.entity.DefaultSchedule;
import com.schedule.app.form.DefaultScheduleForm;
import com.schedule.app.record.input.DefaultScheduleInputRecord;
import com.schedule.app.record.input.ScheduleSearchRecord;
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
    public void postDefaultScheduleService(DefaultScheduleForm form, String userId){
        DefaultSchedule DefaultSchedule = toDefaultScheduleEintity(form, userId);
        DefaultScheduleInputRecord record = toDefaultScheduleRecord(DefaultSchedule);
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
    public DefaultSchedule toDefaultScheduleEintity(DefaultScheduleForm form, String userId){
        DefaultSchedule entity = DefaultSchedule.builder()
                                        .userId(userId) //ログイン機能を使用するか仮に
                                        .startTime(form.startTime())
                                        .endTime(form.endTime())
                                        .startDate(form.startDate())
                                        .endDate(form.endDate())
                                        .workTypeId(form.workTypeId())
                                        .build();
        return entity;
    }
    
    /**
     * エンティティをレコードに変換する
     * 
     * @param DefaultSchedule デフォルトスケジュールエンティティ
     * @return デフォルトスケジュール入力レコード
     */
    @Override
    public DefaultScheduleInputRecord toDefaultScheduleRecord(DefaultSchedule DefaultSchedule){

        // 重複チェック用の検索レコードを作成
        ScheduleSearchRecord scheduleSearchRecord = ScheduleSearchRecord.builder()
                                        .from(DefaultSchedule.getStartDate())
                                        .to(DefaultSchedule.getEndDate())
                                        .build();

        // デフォルトスケジュールの重複チェック
        defaultScheduleService.checkDefaultSchedule(scheduleSearchRecord,DefaultSchedule);

        DefaultScheduleInputRecord record = DefaultScheduleInputRecord.builder()
                                                .userId(DefaultSchedule.getUserId()) //ログイン機能を使用するか仮に
                                                .startTime(DefaultSchedule.getStartTime())
                                                .endTime(DefaultSchedule.getEndTime())
                                                .startDate(DefaultSchedule.getStartDate())
                                                .endDate(DefaultSchedule.getEndDate())
                                                .workTypeId(DefaultSchedule.getWorkTypeId())
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
