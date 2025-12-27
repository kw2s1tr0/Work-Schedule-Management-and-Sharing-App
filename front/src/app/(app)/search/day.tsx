import { ScheduleDTO } from '@/type/dto/schedule.dto';
import styles from './day.module.css';

type Props = {
  scheduleDTO: ScheduleDTO;
};

export default function Day({ scheduleDTO }: Props) {
  const { startTime, endTime, date, worktypeName, worktypeColor } = scheduleDTO;

  return (
    <div className={styles.day}>
      <p className={styles.date}>{date}</p>
      <p className={styles.time}>
        {startTime}-{endTime}
      </p>
      <p className={styles.worktype} style={{ backgroundColor: worktypeColor }}>
        {worktypeName}
      </p>
    </div>
  );
}
