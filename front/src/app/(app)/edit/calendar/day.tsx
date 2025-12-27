import { ScheduleDTO } from '@/type/dto/schedule.dto';
import styles from './day.module.css';

type Props = {
  scheduleDTO: ScheduleDTO;
  index: number;
};

export default function Day({ scheduleDTO, index }: Props) {
  const { startTime, endTime, date, worktypeName, worktypeColor } = scheduleDTO;

  return (
    <div className={styles.day}>
      <p className={styles.date}>{date}</p>
      {(startTime || endTime) && (
        <p className={styles.time}>
          {startTime}-{endTime}
        </p>
      )}
      <p className={styles.worktype} style={{ backgroundColor: worktypeColor }}>
        {worktypeName}
      </p>
    </div>
  );
}
