package com.schedule.app.applicationservice;

import java.util.List;

import com.schedule.app.dto.OrganizationDTO;
import com.schedule.app.record.output.OrganizationRecord;

public interface GetOrganizationService {
    List<OrganizationDTO> getOrganizationList();
    List<OrganizationRecord> readOrganizationList();
    List<OrganizationDTO> toOrganizationDTOList(List<OrganizationRecord> records);
}
