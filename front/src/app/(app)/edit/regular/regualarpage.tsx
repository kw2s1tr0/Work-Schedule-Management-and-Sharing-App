'use client';

import { RegularscheduleDTO } from "@/type/dto/regularschedule.dto";
import Schedule from "./schedule";

type Props = {
    regularscheduleDTOList: RegularscheduleDTO[];
};

export default function RegularPage({ regularscheduleDTOList }: Props) {
  return (
    <>
      <form>
        <label htmlFor="from">開始日</label>
        <input type="date" id="from" name="from" />
        <label htmlFor="to">終了日</label>
        <input type="date" id="to" name="to" />
        <button type="button">検索</button>
      </form>
      {regularscheduleDTOList.map((regularscheduleDTO) => (
        <Schedule key={regularscheduleDTO.scheduleId} regularscheduleDTO={regularscheduleDTO} />
      ))}
    </>
  );
}
