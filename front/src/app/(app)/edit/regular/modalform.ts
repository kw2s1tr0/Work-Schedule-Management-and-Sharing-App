import { DayOfWeek } from '@/enum/dayofweek.enum';

export type Modalform = {
  id?: string;
  startDate: string;
  endDate: string;
  startTime: string;
  endTime: string;
  dayOfWeek: DayOfWeek;
  workTypeId: string;
};
