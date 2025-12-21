import { ScheduleDTO } from "@/type/dto/schedule.dto";
import React from "react";

type Props = {
    scheduleDTO: ScheduleDTO;
};

export default function Day({ scheduleDTO }: Props) {

    const { startTime, endTime, worktypeName, worktypeColor } = scheduleDTO;

  return (
    <React.Fragment>
      <div>
        <p>{startTime}-{endTime}</p>
        <p style={{ backgroundColor: worktypeColor }}>{worktypeName}</p>
      </div>
    </React.Fragment>
  );
}