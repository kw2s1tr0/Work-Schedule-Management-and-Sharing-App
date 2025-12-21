import { RegularscheduleDTO } from "@/type/dto/regularschedule.dto";
import React from "react";

type Props = {
    regularscheduleDTO: RegularscheduleDTO;
};

export default function Schedule({ regularscheduleDTO }: Props) {

    const { scheduleId, daysOfWeek, startTime, endTime, startDate, endDate, worktypeName, worktypeColor } = regularscheduleDTO;
  return (
    <>
    <React.Fragment>
      <div>
        <p>{startTime}-{endTime}</p>
        <p>{startDate}-{endDate}</p>
        <p>{daysOfWeek}</p>
        <p style={{ backgroundColor: worktypeColor }}>{worktypeName}</p>
      </div>
    </React.Fragment>
    </>
  );
}