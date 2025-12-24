import { ScheduleDTO } from "@/type/dto/schedule.dto";
import React from "react";

type Props = {
    scheduleDTO: ScheduleDTO;
    index: number;
};

export default function Day({ scheduleDTO, index }: Props) {

    const { startTime, endTime, date, worktypeName, worktypeColor } = scheduleDTO;

  return (
    <React.Fragment>
      <div>
        <p>{date}</p>
        {(startTime || endTime) && <p>{startTime}-{endTime}</p>}
        <p style={{ backgroundColor: worktypeColor }}>{worktypeName}</p>
      </div>
      {(index + 1) % 7 === 0 && <br />}
    </React.Fragment>
  );
}