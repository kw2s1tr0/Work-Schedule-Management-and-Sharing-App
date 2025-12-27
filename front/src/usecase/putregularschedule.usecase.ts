import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { PutRegularScheduleForm } from '@/type/form/putregularschedule.form';
import { PutRegularScheduleReq } from '@/type/req/putregularschedule.req';

/**
 * レギュラスケジュール更新ユースケース
 * @param putRegularScheduleForm レギュラスケジュール更新フォーム
 * @param type サーバーorクライアント
 * @returns
 */
export async function PutRegularScheduleUsecase(
  putRegularScheduleForm: PutRegularScheduleForm,
  type: ServerOrClientEnum,
): Promise<void> {
  const putRegularScheduleReq: PutRegularScheduleReq = toreq(
    putRegularScheduleForm,
  );
  await put(putRegularScheduleReq, type);
}

/**
 * レギュラスケジュール更新リクエスト変換
 * @param putRegularScheduleForm レギュラスケジュール更新フォーム
 * @returns レギュラスケジュール更新リクエスト
 */
function toreq(
  putRegularScheduleForm: PutRegularScheduleForm,
): PutRegularScheduleReq {
  const putRegularScheduleReq: PutRegularScheduleReq = {
    id: putRegularScheduleForm.id,
    startTime: putRegularScheduleForm.startTime,
    endTime: putRegularScheduleForm.endTime,
    startDate: putRegularScheduleForm.startDate,
    endDate: putRegularScheduleForm.endDate,
    dayOfWeek: putRegularScheduleForm.dayOfWeek.toString(),
    workTypeId: putRegularScheduleForm.workTypeId,
  };
  return putRegularScheduleReq;
}

/**
 * レギュラスケジュール更新処理
 * @param putRegularScheduleReq レギュラスケジュール更新リクエスト
 * @param type サーバーorクライアント
 * @returns
 */
async function put(
  putRegularScheduleReq: PutRegularScheduleReq,
  type: ServerOrClientEnum,
): Promise<void> {
  const response = await fetcher(
    `/api/regularSchedule`,
    MethodEnum.PUT,
    type,
    putRegularScheduleReq,
  );

  // エラーチェック
  if (!(200 <= response.status && response.status < 300)) {
    const data = await response.json();
    throw new ExpectedError(response.status, [data.message]);
  }

  return;
}
