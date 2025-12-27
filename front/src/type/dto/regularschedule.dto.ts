import { DayOfWeek } from '@/enum/dayofweek.enum';

/**
 * レギュラスケジュールDTO
 */
export type RegularscheduleDTO = {
  scheduleId: string;
  daysOfWeek: DayOfWeek;
  startTime: string;
  endTime: string;
  startDate: string;
  endDate: string;
  worktypeName: string;
  worktypeColor: string;
};
