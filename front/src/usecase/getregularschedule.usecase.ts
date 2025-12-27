import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';
import { GetSingleScheduleReq } from '@/type/req/getsingleschedule.req';
import { GetSingleScheduleForm } from '@/type/form/getsingleschedule.form';
import { RegularscheduleRes } from '@/type/res/regularschedule.res';
import { RegularscheduleDTO } from '@/type/dto/regularschedule.dto';
import { DayOfWeek } from '@/enum/dayofweek.enum';

/**
 * レギュラスケジュール取得ユースケース
 * @param getSingleScheduleForm レギュラスケジュール取得フォーム
 * @param type サーバーorクライアント
 * @param cookie クッキー
 * @returns レギュラスケジュールDTO配列
 */
export async function GetRegularScheduleUsecase(
  getSingleScheduleForm: GetSingleScheduleForm,
  type: ServerOrClientEnum,
  cookie?: string,
): Promise<RegularscheduleDTO[]> {
  const getSingleScheduleReq: GetSingleScheduleReq = toreq(
    getSingleScheduleForm,
  );
  const regularscheduleResList: RegularscheduleRes[] = await get(
    getSingleScheduleReq,
    type,
    cookie,
  );
  const regularScheduleDTOList: RegularscheduleDTO[] = toDTO(
    regularscheduleResList,
  );
  return regularScheduleDTOList;
}

/**
 * レギュラスケジュール取得リクエスト変換
 * @param getSingleScheduleForm レギュラスケジュール取得フォーム
 * @returns レギュラスケジュール取得リクエスト
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
 * レギュラスケジュール取得処理
 * @param getSingleScheduleReq レギュラスケジュール取得リクエスト
 * @param type サーバーorクライアント
 * @param cookie クッキー
 * @returns レギュラスケジュールレスポンス配列
 */
async function get(
  getSingleScheduleReq: GetSingleScheduleReq,
  type: ServerOrClientEnum,
  cookie?: string,
): Promise<RegularscheduleRes[]> {
  const params = new URLSearchParams();
  if (getSingleScheduleReq.from) {
    params.append('from', getSingleScheduleReq.from);
  }
  if (getSingleScheduleReq.to) {
    params.append('to', getSingleScheduleReq.to);
  }

  const response = await fetcher(
    `/api/regularSchedule?${params.toString()}`,
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

  const regularscheduleResList: RegularscheduleRes[] =
    data as RegularscheduleRes[];

  return regularscheduleResList;
}

/** * レギュラスケジュールレスポンスをDTOに変換
 * @param regularscheduleResList レギュラスケジュールレスポンス配列
 * @returns レギュラスケジュールDTO配列
 */
function toDTO(
  regularscheduleResList: RegularscheduleRes[],
): RegularscheduleDTO[] {
  const regularScheduleDTOList: RegularscheduleDTO[] =
    regularscheduleResList.map((regularscheduleRes) => {
      const regularScheduleDTO: RegularscheduleDTO = {
        scheduleId: regularscheduleRes.scheduleId,
        daysOfWeek: regularscheduleRes.daysOfWeek as DayOfWeek,
        startTime: regularscheduleRes.startTime,
        endTime: regularscheduleRes.endTime,
        startDate: regularscheduleRes.startDate,
        endDate: regularscheduleRes.endDate,
        worktypeName: regularscheduleRes.worktypeName,
        worktypeColor: regularscheduleRes.worktypeColor,
      };
      return regularScheduleDTO;
    });
  return regularScheduleDTOList;
}
