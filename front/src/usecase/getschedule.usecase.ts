import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { GetScheduleReq } from '@/type/req/getschedule.req';
import { GetScheduleForm } from '@/type/form/getschedule.form';
import { ViewModeEnum } from '@/enum/viewmode.enum';
import { ScheduleRes } from '@/type/res/schedule.res';
import { UserRes } from '@/type/res/user.res';
import { UserDTO } from '@/type/dto/user.dto';
import { ScheduleDTO } from '@/type/dto/schedule.dto';
import { ScheduleEnum } from '@/enum/schedule.enum';

export async function GetScheduleUsecase(
  getScheduleForm: GetScheduleForm,
  type: ServerOrClientEnum,
  cookie?: string,
): Promise<UserDTO[]> {
  const getScheduleReq: GetScheduleReq = toreq(getScheduleForm);
  const userResList: UserRes[] = await get(getScheduleReq, type, cookie);
  const userDTOList: UserDTO[] = toDTO(userResList);
  return userDTOList;
}

function toreq(getScheduleForm: GetScheduleForm): GetScheduleReq {
  const getScheduleReq: GetScheduleReq = {
    userId: getScheduleForm.userId,
    week: getScheduleForm.week,
    month: getScheduleForm.month,
    name: getScheduleForm.name,
    viewMode: ViewModeEnum.WEEK,
    organizationCode: getScheduleForm.organizationCode,
  };
  return getScheduleReq;
}

async function get(
  getScheduleReq: GetScheduleReq,
  type: ServerOrClientEnum,
  cookie?: string,
): Promise<UserRes[]> {
  const params = new URLSearchParams();

  if (getScheduleReq.userId) {
    params.append('userId', getScheduleReq.userId);
  }
  if (getScheduleReq.week) {
    params.append('week', getScheduleReq.week);
  }
  if (getScheduleReq.month) {
    params.append('month', getScheduleReq.month);
  }
  if (getScheduleReq.name) {
    params.append('name', getScheduleReq.name);
  }
  if (getScheduleReq.viewMode) {
    params.append('viewMode', getScheduleReq.viewMode);
  }
  if (getScheduleReq.organizationCode) {
    params.append('organizationCode', getScheduleReq.organizationCode);
  }

  const response = await fetcher(
    `/api/schedule?${params.toString()}`,
    MethodEnum.GET,
    type,
    undefined,
    cookie,
  );

  const data = await response.json();

  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [data.message]);
  }

  const userResList: UserRes[] = data as UserRes[];

  return userResList;
}

function toDTO(userResList: UserRes[]): UserDTO[] {
  const userDTOList: UserDTO[] = userResList.map((userRes) => {
    const scheduleDTOList: ScheduleDTO[] = userRes.schedules.map(
      (scheduleRes: ScheduleRes) => {
        const scheduleDTO: ScheduleDTO = {
          scheduleId: scheduleRes.scheduleId,
          date: scheduleRes.date,
          startTime: scheduleRes.startTime,
          endTime: scheduleRes.endTime,
          worktypeName: scheduleRes.worktypeName,
          worktypeColor: scheduleRes.worktypeColor,
          scheduleType: scheduleRes.scheduleType as unknown as ScheduleEnum,
        };
        return scheduleDTO;
      },
    );

    const userDTO: UserDTO = {
      userName: userRes.userName,
      organizationName: userRes.organizationName,
      schedules: scheduleDTOList,
    };
    return userDTO;
  });
  return userDTOList;
}
