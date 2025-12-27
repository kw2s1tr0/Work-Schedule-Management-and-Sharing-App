import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { PutIrregularScheduleForm } from '@/type/form/putirregularschedule.form';
import { PutIrregularScheduleReq } from '@/type/req/putirregularschedule.req';

export async function PutIrregularScheduleUsecase(
  putIrregularScheduleForm: PutIrregularScheduleForm,
  type: ServerOrClientEnum,
): Promise<void> {
  const putIrregularScheduleReq: PutIrregularScheduleReq = toreq(
    putIrregularScheduleForm,
  );
  await put(putIrregularScheduleReq, type);
}

function toreq(
  putIrregularScheduleForm: PutIrregularScheduleForm,
): PutIrregularScheduleReq {
  const putIrregularScheduleReq: PutIrregularScheduleReq = {
    id: putIrregularScheduleForm.id,
    startTime: putIrregularScheduleForm.startTime,
    endTime: putIrregularScheduleForm.endTime,
    date: putIrregularScheduleForm.date,
    workTypeId: putIrregularScheduleForm.workTypeId,
  };
  return putIrregularScheduleReq;
}

async function put(
  putIrregularScheduleReq: PutIrregularScheduleReq,
  type: ServerOrClientEnum,
): Promise<void> {
  const response = await fetcher(
    `/api/irregularSchedule`,
    MethodEnum.PUT,
    type,
    putIrregularScheduleReq,
  );

  const text = await response.text();
  const data = text ? JSON.parse(text) : null;

  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [
      data?.message || 'Unknown error',
    ]);
  }

  return;
}
