'use client';

import { ScheduleDTO } from '@/type/dto/schedule.dto';
import { useState } from 'react';
import Day from './day';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { GetMyScheduleUsecase } from '@/usecase/getmyschedule.usecase';
import { GetScheduleForm } from '@/type/form/getschedule.form';
import { PrevipoutOrNextEnum } from '@/enum/previoutornext.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import styles from './calenderpage.module.css';

type Props = {
  scheduleDTOlist: ScheduleDTO[];
  month: string;
};

/**
 * カレンダーページコンポーネント
 * @param scheduleDTOlist スケジュールデータ転送オブジェクトリスト
 * @param month 月
 * @returns カレンダーページコンポーネント
 */
export default function Calenderpage({ scheduleDTOlist, month }: Props) {
  const [scheduleDTOlistState, setScheduleDTOlistState] =
    useState<ScheduleDTO[]>(scheduleDTOlist);
  const [monthState, setMonthState] = useState<string>(month);

  // スケジュール取得処理
  const fetchSchedules = async () => {
    const getScheduleForm: GetScheduleForm = {
      userId: '',
      week: '',
      month: monthState,
      name: '',
      organizationCode: '',
    };

    try {
      const scheduleDTOlistFiltered: ScheduleDTO[] = await GetMyScheduleUsecase(
        getScheduleForm,
        ServerOrClientEnum.CLIENT,
      );
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

  // 月移動処理
  const moveMonth = (PrevipoutOrNext: PrevipoutOrNextEnum) => {
    const [year, month] = monthState.split('-').map(Number);
    let newMonth: string;
    switch (PrevipoutOrNext) {
      case PrevipoutOrNextEnum.PREVIOUS:
        const prevMonth = month === 1 ? 12 : month - 1;
        const prevYear = month === 1 ? year - 1 : year;
        newMonth = `${prevYear}-${String(prevMonth).padStart(2, '0')}`;
        setMonthState(newMonth);
        break;
      case PrevipoutOrNextEnum.NEXT:
        const nextMonth = month === 12 ? 1 : month + 1;
        const nextYear = month === 12 ? year + 1 : year;
        newMonth = `${nextYear}-${String(nextMonth).padStart(2, '0')}`;
        setMonthState(newMonth);
        break;
    }
    fetchSchedules();
  };

  return (
    <>
      <form className={styles.form}>
        <div className={styles.formGroup}>
          <label htmlFor="month" className={styles.label}>
            月
          </label>
          <input
            type="month"
            id="month"
            name="month"
            className={styles.input}
            value={monthState}
            onChange={(e) => setMonthState(e.target.value)}
          />
        </div>
        <div className={styles.buttonGroup}>
          <button
            type="button"
            className={styles.button}
            onClick={() => moveMonth(PrevipoutOrNextEnum.PREVIOUS)}
          >
            前月へ
          </button>
          <button
            type="button"
            className={styles.button}
            onClick={() => moveMonth(PrevipoutOrNextEnum.NEXT)}
          >
            次月へ
          </button>
        </div>
      </form>
      <div className={styles.weekHeader}>
        <div>月</div>
        <div>火</div>
        <div>水</div>
        <div>木</div>
        <div>金</div>
        <div>土</div>
        <div>日</div>
      </div>
      <div className={styles.calendar}>
        {scheduleDTOlistState.map((scheduleDTO, index) => (
          <Day key={`${scheduleDTO.date}-${index}`} scheduleDTO={scheduleDTO} />
        ))}
      </div>
    </>
  );
}
