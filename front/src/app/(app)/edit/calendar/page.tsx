import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ScheduleDTO } from '@/type/dto/schedule.dto';
import { GetScheduleForm } from '@/type/form/getschedule.form';
import { GetMyScheduleUsecase } from '@/usecase/getmyschedule.usecase';
import { headers } from 'next/headers';
import Calenderpage from './calenderpage';
import styles from './page.module.css';

export const dynamic = 'force-dynamic';

/**
 * カレンダーページコンポーネント
 * @returns カレンダーページコンポーネント
 */
export default async function Calender() {
  // サーバーサイドでヘッダー情報を取得
  const headerList = await headers();
  const cookie = headerList.get('cookie') ?? '';

  // 現在の年月を取得
  const date = new Date();
  const month = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}`;

  const getscheduleForm: GetScheduleForm = {
    userId: '',
    week: '',
    month: month,
    name: '',
    organizationCode: '',
  };

  // スケジュール取得処理
  const scheduleDTOlist: ScheduleDTO[] = await GetMyScheduleUsecase(
    getscheduleForm,
    ServerOrClientEnum.SERVER,
    cookie,
  );

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Edit</h1>
      <h2 className={styles.subtitle}>Calendar</h2>
      <Calenderpage scheduleDTOlist={scheduleDTOlist} month={month} />
    </div>
  );
}
