import { headers } from "next/headers";
import DefaultPage from "./defaultpage";
import { GetSingleScheduleForm } from "@/type/form/getsingleschedule.form";
import { GetDefaultScheduleUsecase } from "@/usecase/getdefaultschedule.usecase";
import { ServerOrClientEnum } from "@/enum/serverOrClient.enum";
import { DefaultScheduleDTO } from "@/type/dto/defaultschedule.dto";

export const dynamic = 'force-dynamic';

export default async function Default() {

  const headerList = await headers();
  const cookie = headerList.get('cookie') ?? '';

  const date = new Date();
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const firstDay = '01';
  const lastDay = new Date(year, date.getMonth() + 1, 0).getDate().toString().padStart(2, '0');

  const from = `${year}-${month}-${firstDay}`;
  const to = `${year}-${month}-${lastDay}`;

  const getSingleScheduleForm: GetSingleScheduleForm = {
    from: from,
    to: to,
  };

  const defaultscheduleDTOList: DefaultScheduleDTO[] = await GetDefaultScheduleUsecase(getSingleScheduleForm, ServerOrClientEnum.SERVER, cookie);

  return (
    <>
      <h1>Edit</h1>
      <h2>Default</h2>
      <DefaultPage defaultscheduleDTOList={defaultscheduleDTOList} />
    </>
  );
}
