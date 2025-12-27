import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';

/**
 * ログアウトユースケース
 * @param type サーバーorクライアント
 * @returns
 */
export async function logoutUsecase(type: ServerOrClientEnum): Promise<void> {
  await get(type);
}

/** * ログアウト処理
 * @param type サーバーorクライアント
 * @returns
 */
async function get(type: ServerOrClientEnum): Promise<void> {
  const response = await fetcher(`/api/logout`, MethodEnum.GET, type);

  const data = await response.json();

  // エラーチェック
  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [data.message]);
  }

  return;
}
