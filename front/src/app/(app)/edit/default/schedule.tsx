import { DefaultScheduleDTO } from "@/type/dto/defaultschedule.dto";
import React from "react";

type Props = {
    defaultscheduleDTO: DefaultScheduleDTO;
};

export default function Default({ defaultscheduleDTO }: Props) {

    const { scheduleId, startTime, endTime, startDate, endDate, worktypeName, worktypeColor } = defaultscheduleDTO;
  return (
    <>
    <React.Fragment>
      <div>
        <p>{startTime}-{endTime}</p>
        <p>{startDate}-{endDate}</p>
        <p style={{ backgroundColor: worktypeColor }}>{worktypeName}</p>
      </div>
    </React.Fragment>
    </>
  );
}
