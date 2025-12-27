import { DayOfWeek } from '@/enum/dayofweek.enum';

/**
 * レギュラスケジュール登録フォーム
 */
export type PostRegularScheduleForm = {
  startTime: string;
  endTime: string;
  startDate: string;
  endDate: string;
  dayOfWeek: DayOfWeek;
  workTypeId: string;
};
