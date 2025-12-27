import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { GetScheduleReq } from '@/type/req/getschedule.req';
import { GetScheduleForm } from '@/type/form/getschedule.form';
import { ViewModeEnum } from '@/enum/viewmode.enum';
import { ScheduleRes } from '@/type/res/schedule.res';
import { UserRes } from '@/type/res/user.res';
import { ScheduleDTO } from '@/type/dto/schedule.dto';
import { ScheduleEnum } from '@/enum/schedule.enum';

export async function GetMyScheduleUsecase(
  getScheduleForm: GetScheduleForm,
  type: ServerOrClientEnum,
  cookie?: string,
): Promise<ScheduleDTO[]> {
  const userId: string = await loginuserinfo(type, cookie);
  const getScheduleReq: GetScheduleReq = toreq(getScheduleForm, userId);
  const userResList: UserRes[] = await get(getScheduleReq, type, cookie);
  const scheduleDTOList: ScheduleDTO[] = toDTO(userResList);
  return scheduleDTOList;
}

async function loginuserinfo(
  type: ServerOrClientEnum,
  cookie?: string,
): Promise<string> {
  const response = await fetcher(
    '/api/me',
    MethodEnum.GET,
    type,
    undefined,
    cookie,
  );

  if (!(200 <= response.status && response.status < 300)) {
    const data = await response.json();
    throw new ExpectedError(response.status, [data.message]);
  }

  const userId: string = await response.text();

  return userId;
}

function toreq(
  getScheduleForm: GetScheduleForm,
  userId: string,
): GetScheduleReq {
  const getScheduleReq: GetScheduleReq = {
    userId: userId,
    week: getScheduleForm.week,
    month: getScheduleForm.month,
    name: getScheduleForm.name,
    viewMode: ViewModeEnum.MONTH,
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

function toDTO(userResList: UserRes[]): ScheduleDTO[] {
  const scheduleResList: ScheduleRes[] = userResList[0].schedules;
  let scheduleDTOList: ScheduleDTO[];

  const datefirst = new Date(scheduleResList[0].date);
  let dayOfWeekfirst = datefirst.getUTCDay();
  const scheduleDTOListFirst: ScheduleDTO[] = [];

  if (dayOfWeekfirst !== 1) {
    if (dayOfWeekfirst === 0) {
      dayOfWeekfirst = 7;
    }
    for (let i = 1; i < dayOfWeekfirst; i++) {
      const scheduleDTO: ScheduleDTO = {
        scheduleId: '',
        date: '',
        startTime: '',
        endTime: '',
        worktypeName: '',
        worktypeColor: '',
        scheduleType: ScheduleEnum.FREE,
      };
      scheduleDTOListFirst.push(scheduleDTO);
    }
  }

  const scheduleDTOListMiddle: ScheduleDTO[] = scheduleResList.map(
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

  const datelast = new Date(scheduleResList[scheduleResList.length - 1].date);
  let dayOfWeeklast = datelast.getUTCDay();

  const scheduleDTOListLast: ScheduleDTO[] = [];

  if (dayOfWeeklast !== 0) {
    if (dayOfWeeklast === 0) {
      dayOfWeeklast = 7;
    }
    for (let i = dayOfWeeklast; i < 7; i++) {
      const scheduleDTO: ScheduleDTO = {
        scheduleId: '',
        date: '',
        startTime: '',
        endTime: '',
        worktypeName: '',
        worktypeColor: '',
        scheduleType: ScheduleEnum.FREE,
      };
      scheduleDTOListLast.push(scheduleDTO);
    }
  }

  scheduleDTOList = [
    ...scheduleDTOListFirst,
    ...scheduleDTOListMiddle,
    ...scheduleDTOListLast,
  ];

  return scheduleDTOList;
}
