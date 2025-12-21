import { DayOfWeek } from "@/enum/dayofweek.enum";

export type PostRegularScheduleForm = {
    startTime: string;
    endTime: string;
    startDate: string;
    endDate: string;
    dayOfWeek: DayOfWeek;
    workTypeId: string;
};