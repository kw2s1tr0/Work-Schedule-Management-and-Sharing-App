package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PutRegularScheduleService;
import com.schedule.app.domainservice.RegularScheduleService;
import com.schedule.app.entity.RegularSchedule;
import com.schedule.app.form.RegularScheduleForm;
import com.schedule.app.record.input.RegularScheduleInputRecord;
import com.schedule.app.repository.ScheduleUpdateMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PutRegularScheduleServiceImpl implements PutRegularScheduleService{
    private final ScheduleUpdateMapper scheduleUpdateMapper;
    private final RegularScheduleService regularScheduleService;

    /**
     * デフォルトスケジュールを更新する
     * 
     * @param form 画面入力フォーム
     * @param userId ユーザーID
     */
    @Override
    public void patchRegularScheduleService(RegularScheduleForm form, String userId){
        RegularSchedule regularSchedule = toRegularScheduleEntity(form, userId);
        RegularScheduleInputRecord record = toRegularScheduleRecord(regularSchedule);
        patchRegularSchedule(record);
    }

    /**
     * フォームをエンティティに変換する
     * 
     * @param form 画面入力フォーム
     * @param userId ユーザーID 
     * @return レギュラースケジュールエンティティ
     */
    @Override
    public RegularSchedule toRegularScheduleEntity(RegularScheduleForm form, String userId){
        RegularSchedule entity = RegularSchedule.builder()
                                        .id(form.id())
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

        // レギュラースケジュールの存在チェック
        regularScheduleService.existRegularSchedule(regularSchedule.getId(), regularSchedule.getUserId());

        // レギュラースケジュールの重複チェック
        regularScheduleService.checkRegularSchedule(regularSchedule.getId(), regularSchedule.getUserId(), regularSchedule.getStartDate(), regularSchedule.getEndDate(),regularSchedule);

        RegularScheduleInputRecord record = RegularScheduleInputRecord.builder()
                                                .id(regularSchedule.getId())
                                                .userId(regularSchedule.getUserId())
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
     * レギュラースケジュールを更新する
     * 
     * @param record レギュラースケジュール入力レコード
     */
    @Override   
    public void patchRegularSchedule(RegularScheduleInputRecord record){
        scheduleUpdateMapper.updateRegularSchedule(record);
    }
}
