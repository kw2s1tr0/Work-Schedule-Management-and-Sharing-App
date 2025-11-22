package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PostRegularScheduleService;
import com.schedule.app.domainservice.RegularScheduleService;
import com.schedule.app.entity.RegularSchedule;
import com.schedule.app.form.RegularScheduleInsertForm;
import com.schedule.app.record.input.RegularScheduleInputRecord;
import com.schedule.app.repository.ScheduleCreateMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostRegularScheduleServiceImpl implements PostRegularScheduleService{
    private final ScheduleCreateMapper scheduleCreateMapper;
    private final RegularScheduleService regularScheduleService;

    /**
     * レギュラースケジュールを登録する
     * 
     * @param form 画面入力フォーム 
     * @param userId ユーザーID
     */
    @Override
    public void postRegularScheduleService(RegularScheduleInsertForm form, String userId){
        RegularSchedule regularSchedule = toRegularScheduleEntity(form, userId);
        RegularScheduleInputRecord record = toRegularScheduleRecord(regularSchedule);
        postRegularSchedule(record);
    }

    /**
     * フォームをエンティティに変換する
     * 
     * @param form 画面入力フォーム
     * @param userId ユーザーID 
     * @return レギュラースケジュールエンティティ
     */
    @Override
    public RegularSchedule toRegularScheduleEntity(RegularScheduleInsertForm form, String userId){
        RegularSchedule entity = RegularSchedule.builder()
                                        .userId(userId) //ログイン機能を使用するか仮に
                                        .startTime(form.startTime())
                                        .endTime(form.endTime())
                                        .startDate(form.startDate())
                                        .endDate(form.endDate())
                                        .dayOfWeek(form.dayOfWeek())
                                        .workTypeId(form.workTypeId())
                                        .build();
        return entity;
    }
    
    /**
     * エンティティをレコードに変換する
     * 
     * @param regularSchedule レギュラースケジュールエンティティ
     * @return レギュラースケジュール入力レコード
     */
    @Override
    public RegularScheduleInputRecord toRegularScheduleRecord(RegularSchedule regularSchedule){

        // レギュラースケジュールの重複チェック
        regularScheduleService.checkRegularSchedule(regularSchedule.getId(), regularSchedule.getUserId(), regularSchedule.getStartDate(), regularSchedule.getEndDate(),regularSchedule);

        RegularScheduleInputRecord record = RegularScheduleInputRecord.builder()
                                                .userId(regularSchedule.getUserId()) //ログイン機能を使用するか仮に
                                                .startTime(regularSchedule.getStartTime())
                                                .endTime(regularSchedule.getEndTime())
                                                .startDate(regularSchedule.getStartDate())
                                                .endDate(regularSchedule.getEndDate())
                                                .dayOfWeek(regularSchedule.getDayOfWeek().name())
                                                .workTypeId(regularSchedule.getWorkTypeId())
                                                .build();

        return record;
    }

    /**
     * レギュラースケジュールを登録する
     * 
     * @param record レギュラースケジュール入力レコード
     */
    @Override
    public void postRegularSchedule(RegularScheduleInputRecord record) {
        scheduleCreateMapper.createRegularSchedule(record);
    }
}
