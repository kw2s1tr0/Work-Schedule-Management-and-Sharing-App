import { ScheduleRes } from './schedule.res';

/**
 * ユーザーレスポンス
 */
export type UserRes = {
  userName: string;
  organizationName: string;
  schedules: ScheduleRes[];
};
