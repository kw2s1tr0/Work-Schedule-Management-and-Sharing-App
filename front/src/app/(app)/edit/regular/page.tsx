import { headers } from "next/headers";
import { GetSingleScheduleForm } from "@/type/form/getsingleschedule.form";
import { ServerOrClientEnum } from "@/enum/serverOrClient.enum";
import { GetRegularScheduleUsecase } from "@/usecase/getregularschedule.usecase";
import { RegularscheduleDTO } from "@/type/dto/regularschedule.dto";
import RegularPage from "./regualarpage";

export const dynamic = 'force-dynamic';

export default async function Regular() {

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

  const regularscheduleDTOList: RegularscheduleDTO[] = await GetRegularScheduleUsecase(getSingleScheduleForm, ServerOrClientEnum.SERVER, cookie);

  return (
    <>
      <h1>Edit</h1>
      <h2>Regular</h2>
      <RegularPage regularscheduleDTOList={regularscheduleDTOList} from={from} to={to} />
    </>
  );
}