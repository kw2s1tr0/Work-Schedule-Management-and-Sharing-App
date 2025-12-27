import { ScheduleRes } from './schedule.res';

export type UserRes = {
  userName: string;
  organizationName: string;
  schedules: ScheduleRes[];
};
