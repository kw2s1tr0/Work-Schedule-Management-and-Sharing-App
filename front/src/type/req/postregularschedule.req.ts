/**
 * レギュラスケジュール登録リクエスト
 */
export type PostRegularScheduleReq = {
  startTime: string;
  endTime: string;
  startDate: string;
  endDate: string;
  dayOfWeek: string;
  workTypeId: string;
};
