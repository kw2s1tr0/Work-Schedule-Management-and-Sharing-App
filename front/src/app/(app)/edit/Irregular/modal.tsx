import { WorkTypeDTO } from '@/type/dto/worktype.dto';
import { PostDefaultScheduleForm } from '@/type/form/postdefaultschedule.form';
import { useEffect, useState } from 'react';
import { Modalform } from './modalform';
import { PostOrPut } from '@/enum/PostOrPut.enum';
import { PutDefaultScheduleForm } from '@/type/form/putdefaultschedule.form';
import { ExpectedError } from '@/Error/ExpectedError';
import { PutIrregularScheduleForm } from '@/type/form/putirregularschedule.form';
import { PostIrregularScheduleForm } from '@/type/form/postirregularschedule.form';
import styles from './modal.module.css';

type Props = {
  worktypeDTOList: WorkTypeDTO[];
  modalform: Modalform;
  postOrPut?: PostOrPut;
  handleUpdate: (
    putIrregularScheduleForm: PutIrregularScheduleForm,
  ) => Promise<void>;
  handleCreate: (
    postIrregularScheduleForm: PostIrregularScheduleForm,
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
  const { id, date, startTime, endTime, workTypeId } = modalform;

  const [startTimestate, setStartTime] = useState<string>('');
  const [endTimestate, setEndTime] = useState<string>('');
  const [datestate, setDate] = useState<string>('');
  const [workTypeIdstate, setWorkTypeId] = useState<string>('');
  const [workTypeColorState, setWorkTypeColorState] = useState<string>('');

  useEffect(() => {
    setStartTime(startTime);
    setEndTime(endTime);
    setDate(date);
    setWorkTypeId(workTypeId);
    handleWorkTypeChange(workTypeId);
  }, [startTime, endTime, date, workTypeId]);

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
    const putIrregularScheduleForm: PutIrregularScheduleForm = {
      id: id,
      date: datestate,
      startTime: startTimestate,
      endTime: endTimestate,
      workTypeId: workTypeIdstate,
    };
    try {
      await handleUpdate(putIrregularScheduleForm);
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
    const postIrregularScheduleForm: PostIrregularScheduleForm = {
      date: datestate,
      startTime: startTimestate,
      endTime: endTimestate,
      workTypeId: workTypeIdstate,
    };
    try {
      await handleCreate(postIrregularScheduleForm);
      closeModal();
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        {postOrPut == PostOrPut.PUT && (
          <h3 className={styles.modalTitle}>スケジュール編集 (ID: {id})</h3>
        )}
        {postOrPut == PostOrPut.POST && (
          <h3 className={styles.modalTitle}>新規スケジュール作成</h3>
        )}
        <div className={styles.formGroup}>
          <label htmlFor="startTime" className={styles.label}>
            開始時間:
          </label>
          <input
            type="time"
            id="startTime"
            name="startTime"
            className={styles.input}
            value={startTimestate}
            onChange={(e) => setStartTime(e.target.value)}
          />
        </div>
        <div className={styles.formGroup}>
          <label htmlFor="endTime" className={styles.label}>
            終了時間:
          </label>
          <input
            type="time"
            id="endTime"
            name="endTime"
            className={styles.input}
            value={endTimestate}
            onChange={(e) => setEndTime(e.target.value)}
          />
        </div>
        <div className={styles.formGroup}>
          <label htmlFor="date" className={styles.label}>
            日付:
          </label>
          <input
            type="date"
            id="date"
            name="date"
            className={styles.input}
            value={datestate}
            onChange={(e) => setDate(e.target.value)}
          />
        </div>
        <div className={styles.formGroup}>
          <label
            style={{ backgroundColor: workTypeColorState }}
            htmlFor="workTypeId"
            className={styles.label}
          >
            勤務タイプ:
          </label>
          <select
            id="workTypeId"
            name="workTypeId"
            className={styles.select}
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
              type="button"
              className={styles.submitButton}
              onClick={handleScheduleUpdate}
            >
              更新
            </button>
          )}
          {postOrPut == PostOrPut.POST && (
            <button
              type="button"
              className={styles.submitButton}
              onClick={handleScheduleCreate}
            >
              作成
            </button>
          )}
          <button
            type="button"
            className={styles.cancelButton}
            onClick={closeModal}
          >
            閉じる
          </button>
        </div>
      </div>
    </div>
  );
}
