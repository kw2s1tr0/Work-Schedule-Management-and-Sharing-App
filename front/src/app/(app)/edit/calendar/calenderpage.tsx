"use client";

import { ScheduleDTO } from "@/type/dto/schedule.dto";
import { WorkTypeDTO } from "@/type/dto/worktype.dto";
import { useEffect, useRef, useState } from "react";
import Day from "./day";
import { ServerOrClientEnum } from "@/enum/serverOrClient.enum";
import { GetMyScheduleUsecase } from "@/usecase/getmyschedule.usecase";
import { GetScheduleForm } from "@/type/form/getschedule.form";
import { PrevipoutOrNextEnum } from "@/enum/previoutornext.enum";
import { ExpectedError } from "@/Error/ExpectedError";

type Props = {
    scheduleDTOlist: ScheduleDTO[];
    workTypeDTOlist: WorkTypeDTO[];
    month: string;
};

export default function Calenderpage({ scheduleDTOlist, workTypeDTOlist, month }: Props) {


    const isFirstRender = useRef(true);

    const [scheduleDTOlistState, setScheduleDTOlistState] = useState<ScheduleDTO[]>(scheduleDTOlist);
    const [monthState, setMonthState] = useState<string>(month);

    useEffect(()  => {
        if (isFirstRender.current) {
            isFirstRender.current = false;
            return
        };
        fetchSchedules();
    }, [monthState]);

    const fetchSchedules = async () => {
      const getScheduleForm: GetScheduleForm = {
        userId: "",
        week: "",
        month: monthState,
        name: "",
        organizationCode: "",
      };

      try {
        const scheduleDTOlistFiltered: ScheduleDTO[] = await GetMyScheduleUsecase(getScheduleForm, ServerOrClientEnum.CLIENT);
        setScheduleDTOlistState(scheduleDTOlistFiltered);
      } catch (error) {
        if (error instanceof ExpectedError) {
          alert(error.messages.join('\n'));
        } else {
          alert('An unexpected error occurred');
        }
        return;
      }
    };

    const moveMonth = (PrevipoutOrNext: PrevipoutOrNextEnum) => {
      const [year, month] = monthState.split("-").map(Number);
      switch (PrevipoutOrNext) {
        case PrevipoutOrNextEnum.PREVIOUS:
          const prevMonth = month === 1 ? 12 : month - 1;
          const prevYear = month === 1 ? year - 1 : year;
          setMonthState(`${prevYear}-${String(prevMonth).padStart(2, '0')}`);
          break;
        case PrevipoutOrNextEnum.NEXT:
          const nextMonth = month === 12 ? 1 : month + 1;
          const nextYear = month === 12 ? year + 1 : year;
          setMonthState(`${nextYear}-${String(nextMonth).padStart(2, '0')}`);
          break;
      }
    }

  return (
    <>
      <form>
        <label htmlFor="month">月</label>
        <input type="month" id="month" name="month" value={monthState} onChange={(e) => setMonthState(e.target.value)} />
        <button type="button" onClick={() => moveMonth(PrevipoutOrNextEnum.PREVIOUS)}>前月へ</button>
        <button type="button" onClick={() => moveMonth(PrevipoutOrNextEnum.NEXT)}>次月へ</button>
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
          <Day key={`${scheduleDTO.date}-${index}`} scheduleDTO={scheduleDTO} index={index} />
        ))}
      </div>
    </>
  );
}
