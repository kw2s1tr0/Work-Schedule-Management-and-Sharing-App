import { ScheduleDTO } from './schedule.dto';

export type UserDTO = {
  userName: string;
  organizationName: string;
  schedules: ScheduleDTO[];
};
