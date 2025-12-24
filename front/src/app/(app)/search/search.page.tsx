"use client";

import { UserDTO } from "@/type/dto/user.dto";
import Day from "./day";
import { useEffect, useState } from "react";
import { OrganizationDTO } from "@/type/dto/organization.dto";
import { GetScheduleUsecase } from "@/usecase/getschedule.usecase";
import { ServerOrClientEnum } from "@/enum/serverOrClient.enum";
import { ExpectedError } from "@/Error/ExpectedError";

type Props = {
  userDTOlist: UserDTO[];
  organizationDTOlist: OrganizationDTO[];
  week: string;
};

export default function Searchpage({ userDTOlist, organizationDTOlist, week }: Props) {

  const [userDTOlistState, setUserDTOlistState] = useState<UserDTO[]>(userDTOlist);
  const [userId, setUserId] = useState<string>("");
  const [weekState, setWeekState] = useState<string>(week);
  const [name, setName] = useState<string>("");
  const [organizationCode, setOrganizationCode] = useState<string>("");

  const handleSearch = async () => {
    const getScheduleForm = {
      userId: userId,
      week: weekState,
      month: "",
      name: name,
      organizationCode: organizationCode,
    };

    try {
      const UserDTOlistFiltered = await GetScheduleUsecase(getScheduleForm, ServerOrClientEnum.CLIENT);

      setUserDTOlistState(UserDTOlistFiltered);
    } catch (error) {
            if (error instanceof ExpectedError) {
              alert(error.messages.join('\n'));
            } else {
              alert('An unexpected error occurred');
            }
            return;
    }
  };

  return (
    <>
      <form>
        <label htmlFor="userId">ユーザーID</label>
        <input type="text" id="userId" name="userId" value={userId} onChange={(e) => setUserId(e.target.value)} />
        <label htmlFor="week">期間</label>
        <input type="week" id="week" name="week" value={weekState} onChange={(e) => setWeekState(e.target.value)} />
        <label htmlFor="name">名前</label>
        <input type="text" id="name" name="name" value={name} onChange={(e) => setName(e.target.value)} />
        <label htmlFor="organizationCode">組織名</label>
        <select name="organizationCode" id="organizationCode" value={organizationCode} onChange={(e) => setOrganizationCode(e.target.value)}>
          <option value="">選択してください</option>
          {organizationDTOlist.map((organizationDTO) => (
            <option key={organizationDTO.organizationCode} value={organizationDTO.organizationCode}>
              {organizationDTO.organizationName}
            </option>
          ))}
        </select>
        <button type="button" onClick={handleSearch}>Search</button>
      </form>
      {userDTOlistState.length === 0 && "該当するデータはありません。"}
      {userDTOlistState.length > 0 && (
      <>
      <p>{userDTOlistState.length}件見つかりました。</p>
      <div>月</div>
      <div>火</div>
      <div>水</div>
      <div>木</div>
      <div>金</div>
      <div>土</div>
      <div>日</div>
      <div className="result">
        {userDTOlistState.map((userDTO) => (
          <div key={userDTO.userName} className="user-schedule">
            <p>{userDTO.organizationName}</p>
            <p>{userDTO.userName}</p>
            <div className="schedules">
              {userDTO.schedules.map((scheduleDTO) => (
                <Day key={scheduleDTO.date} scheduleDTO={scheduleDTO} />
              ))}
            </div>
          </div>
        ))}
      </div>
      </>
      )}
    </>
  );
}