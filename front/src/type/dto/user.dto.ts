import { ScheduleDTO } from './schedule.dto';

/**
 * ユーザーDTO
 */
export type UserDTO = {
  userName: string;
  organizationName: string;
  schedules: ScheduleDTO[];
};
