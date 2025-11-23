package com.schedule.app.applicationservice;

import com.schedule.app.dto.OrganizationDTO;
import com.schedule.app.record.output.OrganizationRecord;
import java.util.List;

public interface GetOrganizationService {
  List<OrganizationDTO> getOrganizationList();

  List<OrganizationRecord> readOrganizationList();

  List<OrganizationDTO> toOrganizationDTOList(List<OrganizationRecord> records);
}
