export type PutRegularScheduleReq = {
    id: string;
    startTime: string;
    endTime: string;
    startDate: string;
    endDate: string;
    dayOfWeek: string;
    workTypeId: string;
};