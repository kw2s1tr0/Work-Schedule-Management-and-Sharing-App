package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PostIrregularScheduleService;
import com.schedule.app.domainservice.IrregularScheduleService;
import com.schedule.app.form.IrregularScheduleForm;
import com.schedule.app.record.input.IrregularScheduleInputRecord;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.repository.ScheduleCreateMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostIrregularScheduleServiceImpl implements PostIrregularScheduleService{

    private final ScheduleCreateMapper scheduleCreateMapper;
    private final IrregularScheduleService irregularScheduleService;

    /**
     * イレギュラースケジュールを登録する
     * 
     * @param form 画面入力フォーム 
     * @param userId ユーザーID
     */
    @Override
    public void postIrregularScheduleService(IrregularScheduleForm form, String userId){
        IrregularScheduleInputRecord record = toIrregularScheduleRecord(form, userId);
        postIrregularSchedule(record);
    }
    
    /**
     * フォームをレコードに変換する
     * 
     * @param form 画面入力フォーム
     * @param userId ユーザーID 
     * @return イレギュラースケジュール入力レコード
     */
    @Override
    public IrregularScheduleInputRecord toIrregularScheduleRecord(IrregularScheduleForm form, String userId){

        // チェック用の検索レコードを作成
        ScheduleSearchRecord scheduleSearchRecord = ScheduleSearchRecord.builder()
                                        .from(form.date())
                                        .to(form.date())
                                        .build();

        // イレギュラースケジュールの重複チェック
        irregularScheduleService.checkIrregularSchedule(scheduleSearchRecord);

        IrregularScheduleInputRecord record = IrregularScheduleInputRecord.builder()
                                                .userId(userId) //ログイン機能を使用するか仮に
                                                .startTime(form.startTime())
                                                .endTime(form.endTime())
                                                .date(form.date())
                                                .workTypeId(form.workTypeId())
                                                .build();

        return record;
    }

    /**
     * イレギュラースケジュールを登録する
     * 
     * @param record イレギュラースケジュール入力レコード
     */
    @Override
    public void postIrregularSchedule(IrregularScheduleInputRecord record) {
        scheduleCreateMapper.createIrregularSchedule(record);
    }
}