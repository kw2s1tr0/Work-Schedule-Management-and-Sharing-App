package com.schedule.app.repository;

import java.util.List;

import com.schedule.app.record.output.WorkTypeRecord;

public interface WorkTypeSearchMapper {
    List<WorkTypeRecord> readWorkTypeRecord();
}
