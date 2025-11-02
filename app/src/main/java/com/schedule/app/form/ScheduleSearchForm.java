package com.schedule.app.form;

public record ScheduleSearchForm (
    Integer userId,
    String week,
    String month,
    String name,
    String organizationName
){}