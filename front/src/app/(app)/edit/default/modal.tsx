import { WorkTypeDTO } from '@/type/dto/worktype.dto';
import { PostDefaultScheduleForm } from '@/type/form/postdefaultschedule.form';
import { useEffect, useState } from 'react';
import { Modalform } from './modalform';
import { PostOrPut } from '@/enum/PostOrPut.enum';
import { PutDefaultScheduleForm } from '@/type/form/putdefaultschedule.form';
import { ExpectedError } from '@/Error/ExpectedError';
import styles from './modal.module.css';

type Props = {
  worktypeDTOList: WorkTypeDTO[];
  modalform: Modalform;
  postOrPut?: PostOrPut;
  handleUpdate: (
    putDefaultScheduleForm: PutDefaultScheduleForm,
  ) => Promise<void>;
  handleCreate: (
    postDefaultScheduleForm: PostDefaultScheduleForm,
  ) => Promise<void>;
  closeModal: () => void;
};

export default function Modal({
  worktypeDTOList,
  modalform,
  postOrPut,
  handleUpdate,
  handleCreate,
  closeModal,
}: Props) {
  const { id, startDate, endDate, startTime, endTime, workTypeId } = modalform;

  const [startTimestate, setStartTime] = useState<string>('');
  const [endTimestate, setEndTime] = useState<string>('');
  const [startDatestate, setStartDate] = useState<string>('');
  const [endDatestate, setEndDate] = useState<string>('');
  const [workTypeIdstate, setWorkTypeId] = useState<string>('');
  const [workTypeColorState, setWorkTypeColorState] = useState<string>('');

  const handleWorkTypeChange = (id: string) => {
    if (!id) {
      setWorkTypeColorState('');
      return;
    }
    const selectedWorkType = worktypeDTOList.find(
      (worktype) => worktype.id === id,
    );
    if (selectedWorkType) {
      setWorkTypeColorState(selectedWorkType.workTypeColor);
    }
  };

  const handleScheduleUpdate = async () => {
    if (!id) return;
    const putDefaultScheduleForm: PutDefaultScheduleForm = {
      id: id,
      startDate: startDatestate,
      endDate: endDatestate,
      startTime: startTimestate,
      endTime: endTimestate,
      workTypeId: workTypeIdstate,
    };
    try {
      await handleUpdate(putDefaultScheduleForm);
      closeModal();
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
    }
  };

  const handleScheduleCreate = async () => {
    const postDefaultScheduleForm: PostDefaultScheduleForm = {
      startDate: startDatestate,
      endDate: endDatestate,
      startTime: startTimestate,
      endTime: endTimestate,
      workTypeId: workTypeIdstate,
    };
    try {
      await handleCreate(postDefaultScheduleForm);
      closeModal();
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
    }
  };

  useEffect(() => {
    setStartTime(startTime);
    setEndTime(endTime);
    setStartDate(startDate);
    setEndDate(endDate);
    setWorkTypeId(workTypeId);
    handleWorkTypeChange(workTypeId);
  }, [startTime, endTime, startDate, endDate, workTypeId]);

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <h2 className={styles.modalTitle}>
          {postOrPut == PostOrPut.PUT ? 'スケジュール編集' : 'スケジュール作成'}
        </h2>
        {postOrPut == PostOrPut.PUT && <p>ID: {id}</p>}

        <div className={styles.formGroup}>
          <label className={styles.label} htmlFor="startTime">
            開始時間:
          </label>
          <input
            className={styles.input}
            type="time"
            id="startTime"
            name="startTime"
            value={startTimestate}
            onChange={(e) => setStartTime(e.target.value)}
          />
        </div>

        <div className={styles.formGroup}>
          <label className={styles.label} htmlFor="endTime">
            終了時間:
          </label>
          <input
            className={styles.input}
            type="time"
            id="endTime"
            name="endTime"
            value={endTimestate}
            onChange={(e) => setEndTime(e.target.value)}
          />
        </div>

        <div className={styles.formGroup}>
          <label className={styles.label} htmlFor="startDate">
            開始日:
          </label>
          <input
            className={styles.input}
            type="date"
            id="startDate"
            name="startDate"
            value={startDatestate}
            onChange={(e) => setStartDate(e.target.value)}
          />
        </div>

        <div className={styles.formGroup}>
          <label className={styles.label} htmlFor="endDate">
            終了日:
          </label>
          <input
            className={styles.input}
            type="date"
            id="endDate"
            name="endDate"
            value={endDatestate}
            onChange={(e) => setEndDate(e.target.value)}
          />
        </div>

        <div className={styles.formGroup}>
          <label
            className={styles.label}
            style={{ backgroundColor: workTypeColorState }}
            htmlFor="workTypeId"
          >
            勤務タイプ:
          </label>
          <select
            className={styles.select}
            id="workTypeId"
            name="workTypeId"
            value={workTypeIdstate}
            onChange={(e) => {
              setWorkTypeId(e.target.value);
              handleWorkTypeChange(e.target.value);
            }}
          >
            <option value="">選択してください</option>
            {worktypeDTOList.map((worktype) => (
              <option key={worktype.id} value={worktype.id}>
                {worktype.workTypeName}
              </option>
            ))}
          </select>
        </div>

        <div className={styles.buttonGroup}>
          {postOrPut == PostOrPut.PUT && (
            <button
              className={styles.submitButton}
              type="button"
              onClick={handleScheduleUpdate}
            >
              更新
            </button>
          )}
          {postOrPut == PostOrPut.POST && (
            <button
              className={styles.submitButton}
              type="button"
              onClick={handleScheduleCreate}
            >
              作成
            </button>
          )}
          <button
            className={styles.cancelButton}
            type="button"
            onClick={closeModal}
          >
            閉じる
          </button>
        </div>
      </div>
    </div>
  );
}
