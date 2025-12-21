"use client";

import { ScheduleDTO } from "@/type/dto/schedule.dto";
import { WorkTypeDTO } from "@/type/dto/worktype.dto";
import { useState } from "react";
import Day from "./day";

type Props = {
    scheduleDTOlist: ScheduleDTO[];
    workTypeDTOlist: WorkTypeDTO[];
    month: string;
};

export default function Calenderpage({ scheduleDTOlist, workTypeDTOlist, month }: Props) {

    const [scheduleDTOlistState, setScheduleDTOlistState] = useState<ScheduleDTO[]>(scheduleDTOlist);
    const [monthState, setMonthState] = useState<string>(month);

  return (
    <>
      <form>
        <label htmlFor="month">月</label>
        <input type="month" id="month" name="month" value={monthState} onChange={(e) => setMonthState(e.target.value)} />
        <button type="button">前へ</button>
        <button type="button">今月</button>
        <button type="button">次へ</button>
      </form>
      <div>月</div>
      <div>火</div>
      <div>水</div>
      <div>木</div>
      <div>金</div>
      <div>土</div>
      <div>日</div>
      <div className="calendar">
        {scheduleDTOlistState.map((scheduleDTO,index) => (
            <>
              <Day key={scheduleDTO.date} scheduleDTO={scheduleDTO} />
              {(index + 1) % 7 === 0 && <br />}
            </>
        ))}
      </div>
    </>
  );
}