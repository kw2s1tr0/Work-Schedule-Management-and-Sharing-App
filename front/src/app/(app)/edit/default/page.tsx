import { headers } from 'next/headers';
import DefaultPage from './defaultpage';
import { GetSingleScheduleForm } from '@/type/form/getsingleschedule.form';
import { GetDefaultScheduleUsecase } from '@/usecase/getdefaultschedule.usecase';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { DefaultScheduleDTO } from '@/type/dto/defaultschedule.dto';
import { WorkTypeDTO } from '@/type/dto/worktype.dto';
import { GetWorkTypeUsecase } from '@/usecase/getworltype.usecase';
import styles from './page.module.css';

export const dynamic = 'force-dynamic';

/**
 * デフォルトスケジュールページコンポーネント
 * @returns デフォルトスケジュールページコンポーネント
 */
export default async function Default() {
  // サーバーサイドでヘッダーを取得
  const headerList = await headers();
  const cookie = headerList.get('cookie') ?? '';

  // 当月の初日と最終日を取得
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

  // デフォルトスケジュールと勤務タイプを取得
  const defaultscheduleDTOList: DefaultScheduleDTO[] =
    await GetDefaultScheduleUsecase(
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
      <h2 className={styles.subtitle}>Default</h2>
      <DefaultPage
        defaultscheduleDTOList={defaultscheduleDTOList}
        worktypeDTOList={worktypeDTOList}
        from={from}
        to={to}
      />
    </div>
  );
}
