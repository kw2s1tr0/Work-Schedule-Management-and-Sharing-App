import { IrregularScheduleDTO } from "@/type/dto/irregularschedule.dto";
import React from "react";

type Props = {
    irregularscheduleDTO: IrregularScheduleDTO;
};

export default function Schedule({ irregularscheduleDTO }: Props) {

    const { scheduleId, startTime, endTime, date, worktypeName, worktypeColor } = irregularscheduleDTO;
  return (
    <>
    <React.Fragment>
      <div>
        <p>{startTime}-{endTime}</p>
        <p>{date}</p>
        <p style={{ backgroundColor: worktypeColor }}>{worktypeName}</p>
      </div>
    </React.Fragment>
    </>
  );
}
