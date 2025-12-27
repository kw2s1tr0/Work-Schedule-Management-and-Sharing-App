import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { PostRegularScheduleForm } from '@/type/form/postregularschedule.form';
import { PostRegularScheduleReq } from '@/type/req/postregularschedule.req';

/**
 * レギュラスケジュール登録ユースケース
 * @param postRegularScheduleForm レギュラスケジュール登録フォーム
 * @param type サーバーorクライアント
 * @returns 登録ID
 */
export async function PostRegularScheduleUsecase(
  postRegularScheduleForm: PostRegularScheduleForm,
  type: ServerOrClientEnum,
): Promise<string> {
  const postRegularScheduleReq: PostRegularScheduleReq = toreq(
    postRegularScheduleForm,
  );
  return await post(postRegularScheduleReq, type);
}

/** *  レギュラスケジュール登録リクエスト変換
 * @param postRegularScheduleForm レギュラスケジュール登録フォーム
 * @returns レギュラスケジュール登録リクエスト
 */
function toreq(
  postRegularScheduleForm: PostRegularScheduleForm,
): PostRegularScheduleReq {
  const postRegularScheduleReq: PostRegularScheduleReq = {
    startTime: postRegularScheduleForm.startTime,
    endTime: postRegularScheduleForm.endTime,
    startDate: postRegularScheduleForm.startDate,
    endDate: postRegularScheduleForm.endDate,
    dayOfWeek: postRegularScheduleForm.dayOfWeek.toString(),
    workTypeId: postRegularScheduleForm.workTypeId,
  };
  return postRegularScheduleReq;
}

/**
 * レギュラスケジュール登録処理
 * @param postRegularScheduleReq レギュラスケジュール登録リクエスト
 * @param type サーバーorクライアント
 * @returns 登録ID
 */
async function post(
  postRegularScheduleReq: PostRegularScheduleReq,
  type: ServerOrClientEnum,
): Promise<string> {
  const response = await fetcher(
    `/api/regularSchedule`,
    MethodEnum.POST,
    type,
    postRegularScheduleReq,
  );

  const data = await response.json();

  
  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [data.message]);
  }

  return data;
}
