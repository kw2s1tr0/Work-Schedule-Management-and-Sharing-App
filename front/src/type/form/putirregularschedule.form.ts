/**
 * イレギュラスケジュール更新フォーム
 */
export type PutIrregularScheduleForm = {
  id: string;
  startTime: string;
  endTime: string;
  date: string;
  workTypeId: string;
};
