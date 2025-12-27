import { DayOfWeek } from '@/enum/dayofweek.enum';

export type PutRegularScheduleForm = {
  id: string;
  startTime: string;
  endTime: string;
  startDate: string;
  endDate: string;
  dayOfWeek: DayOfWeek;
  workTypeId: string;
};
