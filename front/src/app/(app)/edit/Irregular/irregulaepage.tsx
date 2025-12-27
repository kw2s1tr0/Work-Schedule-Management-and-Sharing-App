'use client';

import { useEffect, useState } from "react";
import Schedule from "./schedule";
import { IrregularScheduleDTO } from "@/type/dto/irregularschedule.dto";
import { GetIrregularScheduleUsecase } from "@/usecase/getirregularschedule.usecase";
import { ServerOrClientEnum } from "@/enum/serverOrClient.enum";
import { ExpectedError } from "@/Error/ExpectedError";
import { WorkTypeDTO } from "@/type/dto/worktype.dto";
import { Modalform } from "./modalform";
import { PostOrPut } from "@/enum/PostOrPut.enum";
import { DeleteIrregularScheduleUsecase } from "@/usecase/deleteirregularschedule.usecase";
import { PutIrregularScheduleForm } from "@/type/form/putirregularschedule.form";
import { PutIrregularScheduleUsecase } from "@/usecase/putirregularschedule.usecase";
import { PostIrregularScheduleForm } from "@/type/form/postirregularschedule.form";
import { PostIrregularScheduleUsecase } from "@/usecase/postirregularschedule.usecase";
import Modal from "./modal";

type Props = {
    irregularscheduleDTOList: IrregularScheduleDTO[];
    worktypeDTOList: WorkTypeDTO[];
    from: string;
    to: string;
};

export default function IrregularPage({ irregularscheduleDTOList, worktypeDTOList, from, to }: Props) {

  const [irregularscheduleDTOListState, setIrregularscheduleDTOListState] = useState<IrregularScheduleDTO[]>(irregularscheduleDTOList);

  const [fromState, setFrom] = useState<string>(from);
  const [toState, setTo] = useState<string>(to);

  const [modalOpen, setModalOpen] = useState<boolean>(false);

  const [modalform, setModalform] = useState<Modalform>({
    date: '',
    startTime: '',
    endTime: '',
    workTypeId: ''
  });

  const [postOrPutState, setPostOrPutState] = useState<PostOrPut>();


  const handleSearch = async () => {
    const getSingleScheduleForm = {
      from: fromState,
      to: toState,
    };

    try {
      const irregularscheduleDTOListFiltered: IrregularScheduleDTO[] = await GetIrregularScheduleUsecase(getSingleScheduleForm, ServerOrClientEnum.CLIENT);
      setIrregularscheduleDTOListState(irregularscheduleDTOListFiltered);
    } catch (error) {
 if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');}
      return;
    }
  }

  const handleDelete = async (scheduleId: string) => {
    try {
      const id = await DeleteIrregularScheduleUsecase(scheduleId, ServerOrClientEnum.CLIENT);
      const updatedList = irregularscheduleDTOListState.filter(schedule => schedule.scheduleId !== id);
      setIrregularscheduleDTOListState(updatedList);
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
    }
  };

  const handleUpdate = async (putirregularscheduleform: PutIrregularScheduleForm) => {
    try {
      await PutIrregularScheduleUsecase(putirregularscheduleform, ServerOrClientEnum.CLIENT);

      let workTypeName = '';
      let workTypeColor = '';
      worktypeDTOList.forEach((worktypeDTO) => {
        if (worktypeDTO.id === putirregularscheduleform.workTypeId) {
          workTypeName = worktypeDTO.workTypeName;
          workTypeColor = worktypeDTO.workTypeColor;
        }
      });

      const updatedList: IrregularScheduleDTO[] = [];

      irregularscheduleDTOListState.forEach((irregularscheduleDTO) => {
        if (irregularscheduleDTO.scheduleId === putirregularscheduleform.id) {
          updatedList.push({
            scheduleId: irregularscheduleDTO.scheduleId,
            date: irregularscheduleDTO.date,
            startTime: putirregularscheduleform.startTime,
            endTime: putirregularscheduleform.endTime,
            worktypeName: workTypeName,
            worktypeColor: workTypeColor,
          });
        } else {
          updatedList.push(irregularscheduleDTO);
        }
      });

      setIrregularscheduleDTOListState(updatedList);
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
    }
  };

  const handleCreate = async (postirregularscheduleform: PostIrregularScheduleForm) => {
    try {
      const newId = await PostIrregularScheduleUsecase(postirregularscheduleform, ServerOrClientEnum.CLIENT);
      let workTypeName = '';
      let workTypeColor = '';
      worktypeDTOList.forEach((worktypeDTO) => {
        if (worktypeDTO.id === postirregularscheduleform.workTypeId) {
          workTypeName = worktypeDTO.workTypeName;
          workTypeColor = worktypeDTO.workTypeColor;
        }
      });
      const newIrregularSchedule: IrregularScheduleDTO = {
        scheduleId: newId,
        date: postirregularscheduleform.date,
        startTime: postirregularscheduleform.startTime,
        endTime: postirregularscheduleform.endTime,
        worktypeName: workTypeName,
        worktypeColor: workTypeColor,
      };
      setIrregularscheduleDTOListState([...irregularscheduleDTOListState, newIrregularSchedule]);
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
    }
  };

  const openModal = (postOrPut: PostOrPut, modalform?: Modalform) => {
    let form: Modalform;
    switch (postOrPut) {
      case PostOrPut.POST:
        form = {
          date: '',
          startTime: '',
          endTime: '',
          workTypeId: ''
        };
        break;
      case PostOrPut.PUT:
        form = modalform!;
        break;
    }
    setPostOrPutState(postOrPut);
    setModalform(form);
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  }

  const findWorkTypeId = (worktypeName: string): string => {
    let workTypeId = '';
    worktypeDTOList.forEach((worktypeDTO) => {
      if (worktypeDTO.workTypeName === worktypeName) {
        workTypeId = worktypeDTO.id;
      }
    });
    return workTypeId;
  }

  return (
    <>
      <form>
        <label htmlFor="from">開始日</label>
        <input type="date" id="from" name="from" value={fromState} onChange={(e) => setFrom(e.target.value)} />
        <label htmlFor="to">終了日</label>
        <input type="date" id="to" name="to" value={toState} onChange={(e) => setTo(e.target.value)} />
        <button type="button" onClick={handleSearch}>検索</button>
      </form>
      {irregularscheduleDTOListState.length === 0 && <p>期間内にイレギュラースケジュールは登録されていません。</p>}
      {irregularscheduleDTOListState.length > 0 &&
      irregularscheduleDTOListState.map((irregularscheduleDTO) => (
        <Schedule key={irregularscheduleDTO.scheduleId} irregularscheduleDTO={irregularscheduleDTO} handleDelete={handleDelete} openModal={openModal} findWorkTypeId={findWorkTypeId}/>
      ))}
      <button type="button" onClick={() => openModal(PostOrPut.POST)}>新規作成</button>
      {modalOpen && (
        <Modal worktypeDTOList={worktypeDTOList} postOrPut={postOrPutState!} modalform={modalform} closeModal={closeModal} handleCreate={handleCreate} handleUpdate={handleUpdate} />
      )}
    </>
  );
}
