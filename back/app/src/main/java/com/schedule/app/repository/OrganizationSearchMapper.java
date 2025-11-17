package com.schedule.app.repository;

import java.util.List;

import com.schedule.app.record.output.OrganizationRecord;

public interface OrganizationSearchMapper {
    List<OrganizationRecord> readOrganizationRecord();
}
