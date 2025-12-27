/**
 * イレギュラスケジュール更新リクエスト
 */
export type PutIrregularScheduleReq = {
  id: string;
  startTime: string;
  endTime: string;
  date: string;
  workTypeId: string;
};
