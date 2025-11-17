package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PatchDefaultScheduleService;
import com.schedule.app.domainservice.DefaultScheduleService;
import com.schedule.app.entity.DefaultSchedule;
import com.schedule.app.form.DefaultScheduleForm;
import com.schedule.app.record.input.DefaultScheduleInputRecord;
import com.schedule.app.record.input.ScheduleSearchRecord;
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
        DefaultSchedule DefaultSchedule = toDefaultScheduleEintity(form, userId);
        DefaultScheduleInputRecord record = toDefaultScheduleRecord(DefaultSchedule);
        patchDefaultSchedule(record);
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

        // デフォルトスケジュールの存在チェック
        defaultScheduleService.existDefaultSchedule(DefaultSchedule.getId(), DefaultSchedule.getUserId());

        // チェック用の検索レコードを作成
        ScheduleSearchRecord scheduleSearchRecord = ScheduleSearchRecord.builder()
                                        .from(DefaultSchedule.getStartDate())
                                        .to(DefaultSchedule.getEndDate())
                                        .build();
        
        // デフォルトスケジュールの重複チェック
        defaultScheduleService.checkDefaultSchedule(scheduleSearchRecord,DefaultSchedule);

        DefaultScheduleInputRecord record = DefaultScheduleInputRecord.builder()
                                                .userId(DefaultSchedule.getUserId())
                                                .startTime(DefaultSchedule.getStartTime())
                                                .endTime(DefaultSchedule.getEndTime())
                                                .startDate(DefaultSchedule.getStartDate())
                                                .endDate(DefaultSchedule.getEndDate())
                                                .workTypeId(DefaultSchedule.getWorkTypeId())
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
