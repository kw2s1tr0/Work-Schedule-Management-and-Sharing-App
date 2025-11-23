package com.schedule.app.dto;

import lombok.Builder;

@Builder
public record OrganizationDTO(String organizationCode, String organizationName) {}
