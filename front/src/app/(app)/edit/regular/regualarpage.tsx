'use client';

import { RegularscheduleDTO } from "@/type/dto/regularschedule.dto";
import Schedule from "./schedule";
import { use, useEffect, useState } from "react";
import { GetRegularScheduleUsecase } from "@/usecase/getregularschedule.usecase";
import { ServerOrClientEnum } from "@/enum/serverOrClient.enum";
import { ExpectedError } from "@/Error/ExpectedError";

type Props = {
    regularscheduleDTOList: RegularscheduleDTO[];
    from: string;
    to: string;
};

export default function RegularPage({ regularscheduleDTOList, from, to }: Props) {

  const [regularscheduleDTOListState, setRegularscheduleDTOListState] = useState<RegularscheduleDTO[]>(regularscheduleDTOList);

  const [fromState, setFrom] = useState<string>(from);
  const [toState, setTo] = useState<string>(to);

  const handleSearch = async () => {
    const getSingleScheduleForm = {
      from: fromState,
      to: toState,
    };

    try {
      const regularscheduleDTOListFiltered: RegularscheduleDTO[] = await GetRegularScheduleUsecase(getSingleScheduleForm, ServerOrClientEnum.CLIENT);
      setRegularscheduleDTOListState(regularscheduleDTOListFiltered);
    } catch (error) {
 if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');}
      return;
    }
  }

  return (
    <>
      <form>
        <label htmlFor="from">開始日</label>
        <input type="date" id="from" name="from" value={fromState} onChange={(e) => setFrom(e.target.value)} />
        <label htmlFor="to">終了日</label>
        <input type="date" id="to" name="to" value={toState} onChange={(e) => setTo(e.target.value)} />
        <button type="button" onClick={handleSearch}>検索</button>
      </form>
      {regularscheduleDTOListState.length === 0 && <p>期間内にレギュラースケジュールは登録されていません。</p>}
      {regularscheduleDTOListState.length > 0 &&
      regularscheduleDTOListState.map((regularscheduleDTO) => (
        <Schedule key={regularscheduleDTO.scheduleId} regularscheduleDTO={regularscheduleDTO} />
      ))}
      <button>新規作成</button>
    </>
  );
}
