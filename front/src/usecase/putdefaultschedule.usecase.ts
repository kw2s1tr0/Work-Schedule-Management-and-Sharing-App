import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { PutDefaultScheduleForm } from '@/type/form/putdefaultschedule.form';
import { PutDefaultScheduleReq } from '@/type/req/putdefaultschedule.req';

/**
 * デフォルトスケジュール更新ユースケース
 * @param putDefaultScheduleForm デフォルトスケジュール更新フォーム
 * @param type サーバーorクライアント
 * @returns
 */
export async function PutDefaultScheduleUsecase(
  putDefaultScheduleForm: PutDefaultScheduleForm,
  type: ServerOrClientEnum,
): Promise<void> {
  const putDefaultScheduleReq: PutDefaultScheduleReq = toreq(
    putDefaultScheduleForm,
  );
  await put(putDefaultScheduleReq, type);
}

/** * デフォルトスケジュール更新リクエスト変換
 * @param putDefaultScheduleForm デフォルトスケジュール更新フォーム
 * @returns デフォルトスケジュール更新リクエスト
 */
function toreq(
  putDefaultScheduleForm: PutDefaultScheduleForm,
): PutDefaultScheduleReq {
  const putDefaultScheduleReq: PutDefaultScheduleReq = {
    id: putDefaultScheduleForm.id,
    startTime: putDefaultScheduleForm.startTime,
    endTime: putDefaultScheduleForm.endTime,
    startDate: putDefaultScheduleForm.startDate,
    endDate: putDefaultScheduleForm.endDate,
    workTypeId: putDefaultScheduleForm.workTypeId,
  };
  return putDefaultScheduleReq;
}

/** * デフォルトスケジュール更新処理
 * @param putDefaultScheduleReq デフォルトスケジュール更新リクエスト
 * @param type サーバーorクライアント
 * @returns
 */
async function put(
  putDefaultScheduleReq: PutDefaultScheduleReq,
  type: ServerOrClientEnum,
): Promise<void> {
  const response = await fetcher(
    `/api/defaultSchedule`,
    MethodEnum.PUT,
    type,
    putDefaultScheduleReq,
  );

  // エラーチェック
  if (!(200 <= response.status && response.status < 300)) {
    const data = await response.json();
    throw new ExpectedError(response.status, [data.message]);
  }

  return;
}
