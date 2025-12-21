import { ScheduleEnum } from "@/enum/schedule.enum";

export type ScheduleDTO = {
    scheduleId: string;
    date: string;
    startTime: string;
    endTime: string;
    worktypeName: string;
    worktypeColor: string;
    scheduleType: ScheduleEnum;
};