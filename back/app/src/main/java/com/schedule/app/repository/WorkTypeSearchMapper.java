package com.schedule.app.repository;

import com.schedule.app.record.output.WorkTypeRecord;
import java.util.List;

public interface WorkTypeSearchMapper {
  List<WorkTypeRecord> readWorkTypeRecord();
}
