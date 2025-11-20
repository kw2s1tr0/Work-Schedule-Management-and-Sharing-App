package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PutIrregularScheduleService;
import com.schedule.app.domainservice.IrregularScheduleService;
import com.schedule.app.form.IrregularScheduleForm;
import com.schedule.app.record.input.IrregularScheduleInputRecord;
import com.schedule.app.repository.ScheduleUpdateMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PutIrregularScheduleServiceImpl implements PutIrregularScheduleService{
    
    private final ScheduleUpdateMapper scheduleUpdateMapper;
    private final IrregularScheduleService irregularScheduleService;

    /**
     * イレギュラースケジュールを更新する
     * 
     * @param form 画面入力フォーム
     * @param userId ユーザーID
     */
    @Override
    public void patchIrregularScheduleService(IrregularScheduleForm form, String userId){
        IrregularScheduleInputRecord record = toIrregularScheduleRecord(form, userId);
        patchIrregularSchedule(record);
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

        // イレギュラースケジュールの存在チェック
        irregularScheduleService.existIrregularSchedule(form.id(), userId);

        // イレギュラースケジュールの重複チェック
        irregularScheduleService.checkIrregularSchedule(form.id(), userId, form.date(), form.date());

        IrregularScheduleInputRecord record = IrregularScheduleInputRecord.builder()
                                                .id(form.id())
                                                .userId(userId)
                                                .startTime(form.startTime())
                                                .endTime(form.endTime())
                                                .date(form.date())
                                                .workTypeId(form.workTypeId())
                                                .build();

        return record;
    }

    /**
     * イレギュラースケジュールを更新する
     * 
     * @param record イレギュラースケジュール入力レコード
     */
    @Override
    public void patchIrregularSchedule(IrregularScheduleInputRecord record) {
        scheduleUpdateMapper.updateIrregularSchedule(record);
    }
}
