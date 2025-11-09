package com.schedule.app.record.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRecord {
    private String userId;
    private String name;
    private String organizationName;
}
