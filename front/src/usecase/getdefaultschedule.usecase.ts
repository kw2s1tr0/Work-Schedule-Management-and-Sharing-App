import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { GetSingleScheduleReq } from '@/type/req/getsingleschedule.req';
import { GetSingleScheduleForm } from '@/type/form/getsingleschedule.form';
import { DefaultscheduleRes } from '@/type/res/defaultschedule.res';
import { DefaultScheduleDTO } from '@/type/dto/defaultschedule.dto';

/**
 * デフォルトスケジュール取得ユースケース
 * @param getSingleScheduleForm デフォルトスケジュール取得フォーム
 * @param type サーバーorクライアント
 * @param cookie クッキー
 * @returns デフォルトスケジュールDTO配列
 */
export async function GetDefaultScheduleUsecase(
  getSingleScheduleForm: GetSingleScheduleForm,
  type: ServerOrClientEnum,
  cookie?: string,
): Promise<DefaultScheduleDTO[]> {
  const getSingleScheduleReq: GetSingleScheduleReq = toreq(
    getSingleScheduleForm,
  );
  const defaultscheduleResList: DefaultscheduleRes[] = await get(
    getSingleScheduleReq,
    type,
    cookie,
  );
  const defaultScheduleDTOList: DefaultScheduleDTO[] = toDTO(
    defaultscheduleResList,
  );
  return defaultScheduleDTOList;
}

/**
 * デフォルトスケジュール取得リクエスト変換
 * @param getSingleScheduleForm デフォルトスケジュール取得フォーム
 * @returns デフォルトスケジュール取得リクエスト
 */
function toreq(
  getSingleScheduleForm: GetSingleScheduleForm,
): GetSingleScheduleReq {
  const getSingleScheduleReq: GetSingleScheduleReq = {
    from: getSingleScheduleForm.from,
    to: getSingleScheduleForm.to,
  };
  return getSingleScheduleReq;
}

/**
 * デフォルトスケジュール取得処理
 * @param getSingleScheduleReq デフォルトスケジュール取得リクエスト
 * @param type サーバーorクライアント
 * @param cookie クッキー
 * @returns デフォルトスケジュールレスポンス配列
 */
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
    `/api/defaultSchedule?${params.toString()}`,
    MethodEnum.GET,
    type,
    undefined,
    cookie,
  );

  const data = await response.json();

  // エラーチェック
  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [data.message]);
  }

  const defaultscheduleResList: DefaultscheduleRes[] =
    data as DefaultscheduleRes[];

  return defaultscheduleResList;
}

/**
 * デフォルトスケジュールレスポンスをDTOに変換
 * @param defaultscheduleResList デフォルトスケジュールレスポンス配列
 * @returns デフォルトスケジュールDTO配列
 */
function toDTO(
  defaultscheduleResList: DefaultscheduleRes[],
): DefaultScheduleDTO[] {
  const defaultScheduleDTOList: DefaultScheduleDTO[] =
    defaultscheduleResList.map((defaultscheduleRes) => {
      const defaultScheduleDTO: DefaultScheduleDTO = {
        scheduleId: defaultscheduleRes.scheduleId,
        startTime: defaultscheduleRes.startTime,
        endTime: defaultscheduleRes.endTime,
        startDate: defaultscheduleRes.startDate,
        endDate: defaultscheduleRes.endDate,
        worktypeName: defaultscheduleRes.worktypeName,
        worktypeColor: defaultscheduleRes.worktypeColor,
      };
      return defaultScheduleDTO;
    });
  return defaultScheduleDTOList;
}
