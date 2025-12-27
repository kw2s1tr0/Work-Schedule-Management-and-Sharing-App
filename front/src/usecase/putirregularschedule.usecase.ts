import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { PutIrregularScheduleForm } from '@/type/form/putirregularschedule.form';
import { PutIrregularScheduleReq } from '@/type/req/putirregularschedule.req';

/**
 * イレギュラスケジュール更新ユースケース
 * @param putIrregularScheduleForm イレギュラスケジュール更新フォーム
 * @param type サーバーorクライアント
 * @returns
 */
export async function PutIrregularScheduleUsecase(
  putIrregularScheduleForm: PutIrregularScheduleForm,
  type: ServerOrClientEnum,
): Promise<void> {
  const putIrregularScheduleReq: PutIrregularScheduleReq = toreq(
    putIrregularScheduleForm,
  );
  await put(putIrregularScheduleReq, type);
}

/**
 * イレギュラスケジュール更新リクエスト変換
 * @param putIrregularScheduleForm イレギュラスケジュール更新フォーム
 * @returns イレギュラスケジュール更新リクエスト
 */
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

/**
 * イレギュラスケジュール更新処理
 * @param putIrregularScheduleReq イレギュラスケジュール更新リクエスト
 * @param type サーバーorクライアント
 * @returns
 */
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

  // エラーチェック
  if (!(200 <= response.status && response.status < 300)) {
    const data = await response.json();
    throw new ExpectedError(response.status, [data.message]);
  }

  return;
}
