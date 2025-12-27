/**
 * スケジュール取得リクエスト
 */
export type GetScheduleReq = {
  userId: string;
  week: string;
  month: string;
  viewMode: string;
  name: string;
  organizationCode: string;
};
