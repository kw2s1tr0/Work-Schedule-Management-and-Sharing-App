'use client';

import Schedule from "./schedule";
import { IrregularScheduleDTO } from "@/type/dto/irregularschedule.dto";

type Props = {
    irregularscheduleDTOList: IrregularScheduleDTO[];
};

export default function IrregularPage({ irregularscheduleDTOList }: Props) {
  return (
    <>
      <form>
        <label htmlFor="from">開始日</label>
        <input type="date" id="from" name="from" />
        <label htmlFor="to">終了日</label>
        <input type="date" id="to" name="to" />
        <button type="button">検索</button>
      </form>
      {irregularscheduleDTOList.map((irregularscheduleDTO) => (
        <Schedule key={irregularscheduleDTO.scheduleId} irregularscheduleDTO={irregularscheduleDTO} />
      ))}
    </>
  );
}
