'use client';

import { DefaultScheduleDTO } from "@/type/dto/defaultschedule.dto";
import Schedule from "./schedule";

type Props = {
    defaultscheduleDTOList: DefaultScheduleDTO[];
};

export default function DefaultPage({ defaultscheduleDTOList }: Props) {
  return (
    <>
      <form>
        <label htmlFor="from">開始日</label>
        <input type="date" id="from" name="from" />
        <label htmlFor="to">終了日</label>
        <input type="date" id="to" name="to" />
        <button type="button">検索</button>
      </form>
      {defaultscheduleDTOList.map((defaultscheduleDTO) => (
        <Schedule key={defaultscheduleDTO.scheduleId} defaultscheduleDTO={defaultscheduleDTO} />
      ))}
    </>
  );
}
