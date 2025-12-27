import { PostOrPut } from "@/enum/PostOrPut.enum";
import { ExpectedError } from "@/Error/ExpectedError";
import { RegularscheduleDTO } from "@/type/dto/regularschedule.dto";
import React from "react";

type Props = {
    regularscheduleDTO: RegularscheduleDTO;
    handleDelete: (id: string) => Promise<void>;
    openModal: (postOrPut: any, modalform: any) => void;
    findWorkTypeId: (worktypeName: string) => string;
};

export default function Schedule({ regularscheduleDTO, handleDelete, openModal, findWorkTypeId }: Props) {

    const { scheduleId, daysOfWeek, startTime, endTime, startDate, endDate, worktypeName, worktypeColor } = regularscheduleDTO;

    const opendScheduleModal = () => {
        const workTypeId = findWorkTypeId(worktypeName);
        const modalform = {
            id: scheduleId,
            startDate: startDate,
            endDate: endDate,
            startTime: startTime,
            endTime: endTime,
            dayOfWeek: daysOfWeek,
            workTypeId: workTypeId
        };
        openModal(PostOrPut.PUT, modalform);
    }

  const handlescheduleDelete = async () => {
    try {
      if(!confirm('Deleting schedule with ID: ' + scheduleId)) {
        return;
      }
      await handleDelete(scheduleId);
    } catch (error) {
  if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');}
    }
  } 
  
  return (
    <>
    <React.Fragment>
      <div>
        <p>{startTime}-{endTime}</p>
        <p>{startDate}-{endDate}</p>
        <p>{daysOfWeek}</p>
        <p style={{ backgroundColor: worktypeColor }}>{worktypeName}</p>
        <button onClick={opendScheduleModal}>編集</button>
        <button onClick={handlescheduleDelete}>削除</button>
      </div>
    </React.Fragment>
    </>
  );
}