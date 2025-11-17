package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PatchRegularScheduleService;
import com.schedule.app.domainservice.RegularScheduleService;
import com.schedule.app.entity.RegularSchedule;
import com.schedule.app.form.RegularScheduleForm;
import com.schedule.app.record.input.RegularScheduleInputRecord;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.repository.ScheduleUpdateMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatchRegularScheduleServiceImpl implements PatchRegularScheduleService{
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
     * @param regularSchedule レギュラースケジュールエンティティ
     * @return レギュラースケジュール入力レコード
     */
    @Override 
    public RegularScheduleInputRecord toRegularScheduleRecord(RegularSchedule regularSchedule){

        // レギュラースケジュールの存在チェック
        regularScheduleService.existRegularSchedule(regularSchedule.getId(), regularSchedule.getUserId());

        // チェック用の検索レコードを作成
        ScheduleSearchRecord scheduleSearchRecord = ScheduleSearchRecord.builder()
                                        .from(regularSchedule.getStartDate())
                                        .to(regularSchedule.getEndDate())
                                        .build();

        // レギュラースケジュールの重複チェック
        regularScheduleService.checkRegularSchedule(scheduleSearchRecord, regularSchedule);

        RegularScheduleInputRecord record = RegularScheduleInputRecord.builder()
                                                .userId(regularSchedule.getUserId())
                                                .startTime(regularSchedule.getStartTime())
                                                .endTime(regularSchedule.getEndTime())
                                                .startDate(regularSchedule.getStartDate())
                                                .endDate(regularSchedule.getEndDate())
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
