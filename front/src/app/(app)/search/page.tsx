import { UserDTO } from '@/type/dto/user.dto';
import Searchpage from './search.page';
import { GetScheduleUsecase } from '@/usecase/getschedule.usecase';
import { GetScheduleForm } from '@/type/form/getschedule.form';
import { getISOWeek, getISOWeekYear } from 'date-fns';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { headers } from 'next/headers';
import { GetOrganizationUsecase } from '@/usecase/getorganization.usecase';
import { OrganizationDTO } from '@/type/dto/organization.dto';
import styles from './page.module.css';

export const dynamic = 'force-dynamic';

/**
 * 検索ページコンポーネント
 * @returns 検索ページコンポーネント
 */
export default async function Search() {
  const date = new Date();
  // 現在のISO週を取得
  const week = `${getISOWeekYear(date)}-W${String(getISOWeek(date)).padStart(2, '0')}`;

  const getScheduleForm: GetScheduleForm = {
    userId: '',
    week: week,
    month: '',
    name: '',
    organizationCode: '',
  };

  // サーバーサイドでヘッダー情報を取得
  const headerList = await headers();
  const cookie = headerList.get('cookie') ?? '';

  // スケジュール情報を取得
  const userDTOlist: UserDTO[] = await GetScheduleUsecase(
    getScheduleForm,
    ServerOrClientEnum.SERVER,
    cookie,
  );

  // 組織情報を取得
  const organizationDTOlist: OrganizationDTO[] = await GetOrganizationUsecase(
    ServerOrClientEnum.SERVER,
    cookie,
  );

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Search</h1>
      <Searchpage
        userDTOlist={userDTOlist}
        organizationDTOlist={organizationDTOlist}
        week={week}
      ></Searchpage>
    </div>
  );
}
