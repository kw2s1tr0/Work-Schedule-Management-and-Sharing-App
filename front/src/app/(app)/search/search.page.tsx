"use client";

import { UserDTO } from "@/type/dto/user.dto";
import Day from "./day";
import { useState } from "react";
import { OrganizationDTO } from "@/type/dto/organization.dto";

type Props = {
  userDTOlist: UserDTO[];
  organizationDTOlist: OrganizationDTO[];
};

export default function Searchpage({ userDTOlist, organizationDTOlist }: Props) {

  const [userDTOlistState, setUserDTOlistState] = useState<UserDTO[]>(userDTOlist);
  const [userId, setUserId] = useState<string>("");
  const [week, setWeek] = useState<string>("");
  const [name, setName] = useState<string>("");
  const [organizationCode, setOrganizationCode] = useState<string>("");

  return (
    <>
      <form>
        <label htmlFor="userId">ユーザーID</label>
        <input type="text" id="userId" name="userId" value={userId} onChange={(e) => setUserId(e.target.value)} />
        <label htmlFor="week">期間</label>
        <input type="week" id="week" name="week" value={week} onChange={(e) => setWeek(e.target.value)} />
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
        <button type="button">Search</button>
      </form>
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
  );
}