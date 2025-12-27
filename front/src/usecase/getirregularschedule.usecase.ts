import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { GetSingleScheduleReq } from '@/type/req/getsingleschedule.req';
import { GetSingleScheduleForm } from '@/type/form/getsingleschedule.form';
import { DefaultscheduleRes } from '@/type/res/irregularschedule.res';
import { IrregularScheduleDTO } from '@/type/dto/irregularschedule.dto';

export async function GetIrregularScheduleUsecase(
  getSingleScheduleForm: GetSingleScheduleForm,
  type: ServerOrClientEnum,
  cookie?: string,
): Promise<IrregularScheduleDTO[]> {
  const getSingleScheduleReq: GetSingleScheduleReq = toreq(
    getSingleScheduleForm,
  );
  const irregularscheduleResList: DefaultscheduleRes[] = await get(
    getSingleScheduleReq,
    type,
    cookie,
  );
  const irregularScheduleDTOList: IrregularScheduleDTO[] = toDTO(
    irregularscheduleResList,
  );
  return irregularScheduleDTOList;
}

function toreq(
  getSingleScheduleForm: GetSingleScheduleForm,
): GetSingleScheduleReq {
  const getSingleScheduleReq: GetSingleScheduleReq = {
    from: getSingleScheduleForm.from,
    to: getSingleScheduleForm.to,
  };
  return getSingleScheduleReq;
}

async function get(
  getSingleScheduleReq: GetSingleScheduleReq,
  type: ServerOrClientEnum,
  cookie?: string,
): Promise<DefaultscheduleRes[]> {
  const params = new URLSearchParams();
  if (getSingleScheduleReq.from) {
    params.append('from', getSingleScheduleReq.from);
  }
  if (getSingleScheduleReq.to) {
    params.append('to', getSingleScheduleReq.to);
  }

  const response = await fetcher(
    `/api/irregularSchedule?${params.toString()}`,
    MethodEnum.GET,
    type,
    undefined,
    cookie,
  );

  const data = await response.json();

  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [data.message]);
  }

  const irregularscheduleResList: DefaultscheduleRes[] =
    data as DefaultscheduleRes[];

  return irregularscheduleResList;
}

function toDTO(
  irregularscheduleResList: DefaultscheduleRes[],
): IrregularScheduleDTO[] {
  const irregularScheduleDTOList: IrregularScheduleDTO[] =
    irregularscheduleResList.map((irregularscheduleRes) => {
      const irregularScheduleDTO: IrregularScheduleDTO = {
        scheduleId: irregularscheduleRes.scheduleId,
        date: irregularscheduleRes.date,
        startTime: irregularscheduleRes.startTime,
        endTime: irregularscheduleRes.endTime,
        worktypeName: irregularscheduleRes.worktypeName,
        worktypeColor: irregularscheduleRes.worktypeColor,
      };
      return irregularScheduleDTO;
    });
  return irregularScheduleDTOList;
}
