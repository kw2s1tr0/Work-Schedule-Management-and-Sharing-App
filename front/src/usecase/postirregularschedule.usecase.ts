import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { PostIrregularScheduleForm } from '@/type/form/postirregularschedule.form';
import { PostIrregularScheduleReq } from '@/type/req/postirregularschedule.req';

export async function PostIrregularScheduleUsecase(
  postIrregularScheduleForm: PostIrregularScheduleForm,
  type: ServerOrClientEnum
): Promise<string> {
  const postIrregularScheduleReq: PostIrregularScheduleReq = toreq(postIrregularScheduleForm);
  return await post(postIrregularScheduleReq, type);
}

function toreq(postIrregularScheduleForm: PostIrregularScheduleForm): PostIrregularScheduleReq {
  const postIrregularScheduleReq: PostIrregularScheduleReq = {
    startTime: postIrregularScheduleForm.startTime,
    endTime: postIrregularScheduleForm.endTime,
    date: postIrregularScheduleForm.date,
    workTypeId: postIrregularScheduleForm.workTypeId,
  };
  return postIrregularScheduleReq;
}

async function post(postIrregularScheduleReq: PostIrregularScheduleReq, type: ServerOrClientEnum): Promise<string> {
  const response = await fetcher(`/api/irregularSchedule`, MethodEnum.POST, type, postIrregularScheduleReq);

  const data = await response.json();

  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [data.message]);
  }

  return data;
}
