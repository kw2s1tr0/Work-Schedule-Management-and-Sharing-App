import { headers } from 'next/headers';
import { GetSingleScheduleForm } from '@/type/form/getsingleschedule.form';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { GetIrregularScheduleUsecase } from '@/usecase/getirregularschedule.usecase';
import { IrregularScheduleDTO } from '@/type/dto/irregularschedule.dto';
import IrregularPage from './irregulaepage';
import { GetWorkTypeUsecase } from '@/usecase/getworltype.usecase';
import { WorkTypeDTO } from '@/type/dto/worktype.dto';
import styles from './page.module.css';

export const dynamic = 'force-dynamic';

export default async function Irregular() {
  const headerList = await headers();
  const cookie = headerList.get('cookie') ?? '';

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

  const irregularscheduleDTOList: IrregularScheduleDTO[] =
    await GetIrregularScheduleUsecase(
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
      <h2 className={styles.subtitle}>Irregular</h2>
      <IrregularPage
        irregularscheduleDTOList={irregularscheduleDTOList}
        worktypeDTOList={worktypeDTOList}
        from={from}
        to={to}
      />
    </div>
  );
}
