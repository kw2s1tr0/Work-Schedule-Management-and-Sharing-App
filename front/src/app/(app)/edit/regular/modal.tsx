import { WorkTypeDTO } from '@/type/dto/worktype.dto';
import { useMemo, useState } from 'react';
import { Modalform } from './modalform';
import { PostOrPut } from '@/enum/PostOrPut.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { PutRegularScheduleForm } from '@/type/form/putregularschedule.form';
import { PostRegularScheduleForm } from '@/type/form/postregularschedule.form';
import { DayOfWeek } from '@/enum/dayofweek.enum';
import styles from './modal.module.css';

type Props = {
  worktypeDTOList: WorkTypeDTO[];
  modalform: Modalform;
  postOrPut?: PostOrPut;
  handleUpdate: (
    putRegularScheduleForm: PutRegularScheduleForm,
  ) => Promise<void>;
  handleCreate: (
    postRegularScheduleForm: PostRegularScheduleForm,
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
  const { id, startDate, endDate, startTime, endTime, dayOfWeek, workTypeId } =
    modalform;

  const [startTimestate, setStartTime] = useState<string>(startTime);
  const [endTimestate, setEndTime] = useState<string>(endTime);
  const [startDatestate, setStartDate] = useState<string>(startDate);
  const [endDatestate, setEndDate] = useState<string>(endDate);
  const [workTypeIdstate, setWorkTypeId] = useState<string>(workTypeId);
  const [dayOfWeekState, setDayOfWeek] = useState<DayOfWeek | ''>(dayOfWeek);

  // 勤務タイプカラー取得(stateではなくworkTypeIdstateを参照していることに注意)
  const workTypeColor = useMemo(() => {
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
    const putRegularScheduleForm: PutRegularScheduleForm = {
      id: id,
      startDate: startDatestate,
      endDate: endDatestate,
      startTime: startTimestate,
      endTime: endTimestate,
      dayOfWeek: dayOfWeekState as DayOfWeek,
      workTypeId: workTypeIdstate,
    };
    try {
      await handleUpdate(putRegularScheduleForm);
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
    const postRegularScheduleForm: PostRegularScheduleForm = {
      startDate: startDatestate,
      endDate: endDatestate,
      startTime: startTimestate,
      endTime: endTimestate,
      dayOfWeek: dayOfWeekState as DayOfWeek,
      workTypeId: workTypeIdstate,
    };
    try {
      await handleCreate(postRegularScheduleForm);
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
        <h2 className={styles.modalTitle}>
          {postOrPut == PostOrPut.PUT
            ? '定期スケジュール編集'
            : '定期スケジュール作成'}
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
            style={{ backgroundColor: workTypeColor }}
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

        <div className={styles.formGroup}>
          <label className={styles.label} htmlFor="dayOfWeek">
            曜日:
          </label>
          <select
            className={styles.select}
            id="dayOfWeek"
            name="dayOfWeek"
            value={dayOfWeekState}
            onChange={(e) => setDayOfWeek(e.target.value as DayOfWeek)}
          >
            <option value="">選択してください</option>
            <option value={DayOfWeek.MONDAY}>月曜日</option>
            <option value={DayOfWeek.TUESDAY}>火曜日</option>
            <option value={DayOfWeek.WEDNESDAY}>水曜日</option>
            <option value={DayOfWeek.THURSDAY}>木曜日</option>
            <option value={DayOfWeek.FRIDAY}>金曜日</option>
            <option value={DayOfWeek.SATURDAY}>土曜日</option>
            <option value={DayOfWeek.SUNDAY}>日曜日</option>
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
