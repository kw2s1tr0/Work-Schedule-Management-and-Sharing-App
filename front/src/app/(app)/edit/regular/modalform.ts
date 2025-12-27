import { DayOfWeek } from '@/enum/dayofweek.enum';

/**
 * モーダルフォーム
 */
export type Modalform = {
  id?: string;
  startDate: string;
  endDate: string;
  startTime: string;
  endTime: string;
  dayOfWeek: DayOfWeek;
  workTypeId: string;
};
