import { PostOrPut } from '@/enum/PostOrPut.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { RegularscheduleDTO } from '@/type/dto/regularschedule.dto';
import React from 'react';
import styles from './schedule.module.css';

type Props = {
  regularscheduleDTO: RegularscheduleDTO;
  handleDelete: (id: string) => Promise<void>;
  openModal: (postOrPut: any, modalform: any) => void;
  findWorkTypeId: (worktypeName: string) => string;
};

export default function Schedule({
  regularscheduleDTO,
  handleDelete,
  openModal,
  findWorkTypeId,
}: Props) {
  const {
    scheduleId,
    daysOfWeek,
    startTime,
    endTime,
    startDate,
    endDate,
    worktypeName,
    worktypeColor,
  } = regularscheduleDTO;

  const opendScheduleModal = () => {
    const workTypeId = findWorkTypeId(worktypeName);
    const modalform = {
      id: scheduleId,
      startDate: startDate,
      endDate: endDate,
      startTime: startTime,
      endTime: endTime,
      dayOfWeek: daysOfWeek,
      workTypeId: workTypeId,
    };
    openModal(PostOrPut.PUT, modalform);
  };

  const handlescheduleDelete = async () => {
    try {
      if (!confirm('Deleting schedule with ID: ' + scheduleId)) {
        return;
      }
      await handleDelete(scheduleId);
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
    }
  };

  return (
    <div className={styles.scheduleCard}>
      <div className={styles.scheduleContent}>
        <div className={styles.scheduleInfo}>
          <p className={styles.timeInfo}>
            {startTime}-{endTime}
          </p>
          <p className={styles.dateInfo}>
            {startDate}-{endDate}
          </p>
          <p className={styles.dateInfo}>{daysOfWeek}</p>
        </div>
        <p
          className={styles.worktype}
          style={{ backgroundColor: worktypeColor }}
        >
          {worktypeName}
        </p>
        <div className={styles.buttonGroup}>
          <button className={styles.editButton} onClick={opendScheduleModal}>
            編集
          </button>
          <button
            className={styles.deleteButton}
            onClick={handlescheduleDelete}
          >
            削除
          </button>
        </div>
      </div>
    </div>
  );
}
