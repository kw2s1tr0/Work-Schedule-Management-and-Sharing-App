'use client';

import { useEffect, useState } from "react";
import Schedule from "./schedule";
import { IrregularScheduleDTO } from "@/type/dto/irregularschedule.dto";
import { GetIrregularScheduleUsecase } from "@/usecase/getirregularschedule.usecase";
import { ServerOrClientEnum } from "@/enum/serverOrClient.enum";
import { ExpectedError } from "@/Error/ExpectedError";

type Props = {
    irregularscheduleDTOList: IrregularScheduleDTO[];
    from: string;
    to: string;
};

export default function IrregularPage({ irregularscheduleDTOList, from, to }: Props) {

  const [irregularscheduleDTOListState, setIrregularscheduleDTOListState] = useState<IrregularScheduleDTO[]>(irregularscheduleDTOList);

  const [fromState, setFrom] = useState<string>(from);
  const [toState, setTo] = useState<string>(to);


  const handleSearch = async () => {
    const getSingleScheduleForm = {
      from: fromState,
      to: toState,
    };

    try {
      const irregularscheduleDTOListFiltered: IrregularScheduleDTO[] = await GetIrregularScheduleUsecase(getSingleScheduleForm, ServerOrClientEnum.CLIENT);
      setIrregularscheduleDTOListState(irregularscheduleDTOListFiltered);
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
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
      {irregularscheduleDTOListState.length === 0 && <p>期間内にイレギュラースケジュールは登録されていません。</p>}
      {irregularscheduleDTOListState.length > 0 &&
      irregularscheduleDTOListState.map((irregularscheduleDTO) => (
        <Schedule key={irregularscheduleDTO.scheduleId} irregularscheduleDTO={irregularscheduleDTO} />
      ))}
      <button>新規作成</button>
    </>
  );
}
