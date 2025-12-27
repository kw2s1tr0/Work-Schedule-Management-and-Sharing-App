import { headers } from 'next/headers';
import { GetSingleScheduleForm } from '@/type/form/getsingleschedule.form';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { GetRegularScheduleUsecase } from '@/usecase/getregularschedule.usecase';
import { RegularscheduleDTO } from '@/type/dto/regularschedule.dto';
import RegularPage from './regualarpage';
import { WorkTypeDTO } from '@/type/dto/worktype.dto';
import { GetWorkTypeUsecase } from '@/usecase/getworltype.usecase';
import styles from './page.module.css';

export const dynamic = 'force-dynamic';

/**
 * レギュラースケジュールページコンポーネント
 * @returns レギュラースケジュールページコンポーネント
 */
export default async function Regular() {
  // サーバーサイドでヘッダーからクッキーを取得
  const headerList = await headers();
  const cookie = headerList.get('cookie') ?? '';

  // 今月の初日と最終日を取得
  const date = new Date();
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const firstDay = '01';
  const lastDay = new Date(year, date.getMonth() + 1, 0)
    .getDate()
    .toString()
    .padStart(2, '0');

  const from = `${year}-${month}-${firstDay}`;
  const to = `${year}-${month}-${lastDay}`;

  const getSingleScheduleForm: GetSingleScheduleForm = {
    from: from,
    to: to,
  };

  // レギュラースケジュールと勤務タイプを取得
  const regularscheduleDTOList: RegularscheduleDTO[] =
    await GetRegularScheduleUsecase(
      getSingleScheduleForm,
      ServerOrClientEnum.SERVER,
      cookie,
    );

  const worktypeDTOList: WorkTypeDTO[] = await GetWorkTypeUsecase(
    ServerOrClientEnum.SERVER,
    cookie,
  );

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Edit</h1>
      <h2 className={styles.subtitle}>Regular</h2>
      <RegularPage
        regularscheduleDTOList={regularscheduleDTOList}
        worktypeDTOList={worktypeDTOList}
        from={from}
        to={to}
      />
    </div>
  );
}
