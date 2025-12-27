import { WorkTypeDTO } from '@/type/dto/worktype.dto';
import { useMemo, useState } from 'react';
import { Modalform } from './modalform';
import { PostOrPut } from '@/enum/PostOrPut.enum';
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

/**
 * モーダルコンポーネント
 * @param worktypeDTOList 勤務タイプデータ転送オブジェクトリスト
 * @param modalform モーダルフォーム
 * @param postOrPut POSTかPUTか
 * @param handleUpdate スケジュール更新処理
 * @param handleCreate スケジュール作成処理
 * @param closeModal モーダルクローズ処理
 * @returns モーダルコンポーネント
 */ 
export default function Modal({
  worktypeDTOList,
  modalform,
  postOrPut,
  handleUpdate,
  handleCreate,
  closeModal,
}: Props) {
  const { id, startTime, endTime, date, workTypeId } = modalform;

  const [startTimestate, setStartTime] = useState<string>(startTime);
  const [endTimestate, setEndTime] = useState<string>(endTime);
  const [datestate, setDate] = useState<string>(date);
  const [workTypeIdstate, setWorkTypeId] = useState<string>(workTypeId);

  // 勤務タイプカラー取得(stateではなくworkTypeIdstateを参照していることに注意)
  const workTypeColor = useMemo(() => {
    if (!workTypeIdstate) {
      return '';
    }
    const selectedWorkType = worktypeDTOList.find(
      (worktype) => worktype.id === workTypeIdstate,
    );
    if (!selectedWorkType) {
      return '';
    }
    return selectedWorkType.workTypeColor;
  }, [worktypeDTOList, workTypeIdstate]);

  // スケジュール更新処理
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

  // スケジュール作成処理
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
            style={{ backgroundColor: workTypeColor }}
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
