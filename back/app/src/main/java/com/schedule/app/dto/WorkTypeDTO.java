package com.schedule.app.dto;

import lombok.Builder;

@Builder
public record WorkTypeDTO(String id, String workTypeName, String workTypeColor) {}
