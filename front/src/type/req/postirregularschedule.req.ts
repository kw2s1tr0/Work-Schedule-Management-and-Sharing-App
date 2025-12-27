/**
 * イレギュラスケジュール登録リクエスト
 */
export type PostIrregularScheduleReq = {
  startTime: string;
  endTime: string;
  date: string;
  workTypeId: string;
};
