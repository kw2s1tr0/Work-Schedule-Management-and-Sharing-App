import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';

/**
 * デフォルトスケジュール削除ユースケース
 * @param id デフォルトスケジュールID
 * @param type サーバーorクライアント
 * @returns
 */
export async function DeleteDefaultScheduleUsecase(
  id: string,
  type: ServerOrClientEnum,
): Promise<string> {
  return await deleteSchedule(id, type);
}

/**
 * デフォルトスケジュール削除処理
 * @param id デフォルトスケジュールID
 * @param type サーバーorクライアント
 * @returns
 */
async function deleteSchedule(
  id: string,
  type: ServerOrClientEnum,
): Promise<string> {
  const response = await fetcher(
    `/api/defaultSchedule/${id}`,
    MethodEnum.DELETE,
    type,
  );

  const data = await response.json();

  // エラーチェック
  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [data.message]);
  }

  return data;
}
