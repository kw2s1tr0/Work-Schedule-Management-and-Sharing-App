import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { PostDefaultScheduleForm } from '@/type/form/postdefaultschedule.form';
import { PostDefaultScheduleReq } from '@/type/req/postdefaultschedule.req';

export async function PostDefaultScheduleUsecase(
  postDefaultScheduleForm: PostDefaultScheduleForm,
  type: ServerOrClientEnum,
): Promise<string> {
  const postDefaultScheduleReq: PostDefaultScheduleReq = toreq(
    postDefaultScheduleForm,
  );
  return await post(postDefaultScheduleReq, type);
}

function toreq(
  postDefaultScheduleForm: PostDefaultScheduleForm,
): PostDefaultScheduleReq {
  const postDefaultScheduleReq: PostDefaultScheduleReq = {
    startTime: postDefaultScheduleForm.startTime,
    endTime: postDefaultScheduleForm.endTime,
    startDate: postDefaultScheduleForm.startDate,
    endDate: postDefaultScheduleForm.endDate,
    workTypeId: postDefaultScheduleForm.workTypeId,
  };
  return postDefaultScheduleReq;
}

async function post(
  postDefaultScheduleReq: PostDefaultScheduleReq,
  type: ServerOrClientEnum,
): Promise<string> {
  const response = await fetcher(
    `/api/defaultSchedule`,
    MethodEnum.POST,
    type,
    postDefaultScheduleReq,
  );

  const data = await response.json();

  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [data.message]);
  }

  return data;
}
