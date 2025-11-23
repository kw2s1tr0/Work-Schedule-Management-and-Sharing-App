package com.schedule.app.repository;

import com.schedule.app.record.output.OrganizationRecord;
import java.util.List;

public interface OrganizationSearchMapper {
  List<OrganizationRecord> readOrganizationRecord();
}
