import { IrregularScheduleDTO } from '@/type/dto/irregularschedule.dto';
import { Modalform } from './modalform';
import { PostOrPut } from '@/enum/PostOrPut.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import styles from './schedule.module.css';

type Props = {
  irregularscheduleDTO: IrregularScheduleDTO;
  handleDelete: (id: string) => Promise<void>;
  openModal: (postOrPut: PostOrPut, modalform: Modalform) => void;
  findWorkTypeId: (worktypeName: string) => string;
};

/** イレギュラースケジュールコンポーネント
 * @param irregularscheduleDTO イレギュラースケジュールデータ転送オブジェクト
 * @param handleDelete スケジュール削除処理
 * @param openModal モーダルオープン処理
 * @param findWorkTypeId 勤務タイプID検索処理
 * @returns イレギュラースケジュールコンポーネント
 */
export default function Schedule({
  irregularscheduleDTO,
  handleDelete,
  openModal,
  findWorkTypeId,
}: Props) {
  const { scheduleId, startTime, endTime, date, worktypeName, worktypeColor } =
    irregularscheduleDTO;

  // スケジュール編集モーダルオープン
  const opendScheduleModal = () => {
    const worktypeId = findWorkTypeId(worktypeName);
    const modalform: Modalform = {
      id: scheduleId,
      date: date,
      startTime: startTime,
      endTime: endTime,
      workTypeId: worktypeId,
    };
    openModal(PostOrPut.PUT, modalform);
  };

  // スケジュール削除処理
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
          <p className={styles.dateInfo}>{date}</p>
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
