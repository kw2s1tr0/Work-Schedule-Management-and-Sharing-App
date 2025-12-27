/**
 * デフォルトスケジュール更新リクエスト
 */
export type PutDefaultScheduleReq = {
  id: string;
  startTime: string;
  endTime: string;
  startDate: string;
  endDate: string;
  workTypeId: string;
};
