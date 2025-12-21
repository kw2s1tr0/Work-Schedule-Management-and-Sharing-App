import { UserDTO } from "@/type/dto/user.dto";
import Searchpage from "./search.page";
import { GetScheduleUsecase } from "@/usecase/getschedule.usecase";
import { GetScheduleForm } from "@/type/form/getschedule.form";
import { getISOWeek, getISOWeekYear } from "date-fns";
import { ServerOrClientEnum } from "@/enum/serverOrClient.enum";
import { headers } from "next/headers";
import { GetOrganizationUsecase } from "@/usecase/getorganization.usecase";
import { OrganizationDTO } from "@/type/dto/organization.dto";

export const dynamic = 'force-dynamic';

export default async function Search() {

  const date = new Date();
  const week = `${getISOWeekYear(date)}-W${String(getISOWeek(date)).padStart(2, '0')}`;

  const getScheduleForm: GetScheduleForm = {
    userId: "",
    week: week,
    month: "",
    name: "",
    organizationCode: "",
  };

  const headerList = await headers();
  const cookie = headerList.get('cookie') ?? '';

  const userDTOlist: UserDTO[] = await GetScheduleUsecase(getScheduleForm, ServerOrClientEnum.SERVER, cookie);

  const organizationDTOlist: OrganizationDTO[] = await GetOrganizationUsecase(ServerOrClientEnum.SERVER, cookie);

  return (
    <>
      <h1>Search</h1>
      <Searchpage userDTOlist={userDTOlist} organizationDTOlist={organizationDTOlist}></Searchpage>
    </>
  );
}
