package com.schedule.app.record.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationRecord {
  private String organizationCode;
  private String organizationName;
}
