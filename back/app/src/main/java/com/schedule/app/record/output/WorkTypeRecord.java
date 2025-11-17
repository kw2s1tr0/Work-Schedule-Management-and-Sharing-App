package com.schedule.app.record.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkTypeRecord {
    private String id;
    private String workTypeName;
    private String workTypeColor;
}
