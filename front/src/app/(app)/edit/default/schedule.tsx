import { ExpectedError } from '@/Error/ExpectedError';
import { DefaultScheduleDTO } from '@/type/dto/defaultschedule.dto';
import { Modalform } from './modalform';
import { PostOrPut } from '@/enum/PostOrPut.enum';
import styles from './schedule.module.css';

type Props = {
  defaultscheduleDTO: DefaultScheduleDTO;
  handleDelete: (id: string) => Promise<void>;
  openModal: (postOrPut: PostOrPut, modalform: Modalform) => void;
  findWorkTypeId: (worktypeName: string) => string;
};

/** デフォルトスケジュールコンポーネント
 * @param defaultscheduleDTO デフォルトスケジュールデータ転送オブジェクト
 * @param handleDelete スケジュール削除処理
 * @param openModal モーダルオープン処理
 * @param findWorkTypeId 勤務タイプID検索処理
 * @returns デフォルトスケジュールコンポーネント
 */
export default function Default({
  defaultscheduleDTO,
  handleDelete,
  openModal,
  findWorkTypeId,
}: Props) {
  const {
    scheduleId,
    startTime,
    endTime,
    startDate,
    endDate,
    worktypeName,
    worktypeColor,
  } = defaultscheduleDTO;

  // スケジュール編集モーダルオープン
  const opendScheduleModal = () => {
    const workTypeId = findWorkTypeId(worktypeName);
    const modalform: Modalform = {
      id: scheduleId,
      startDate: startDate,
      endDate: endDate,
      startTime: startTime,
      endTime: endTime,
      workTypeId: workTypeId,
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
          <p className={styles.dateInfo}>
            {startDate}-{endDate}
          </p>
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
