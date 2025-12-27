'use client';

import { RegularscheduleDTO } from "@/type/dto/regularschedule.dto";
import Schedule from "./schedule";
import { use, useEffect, useState } from "react";
import { GetRegularScheduleUsecase } from "@/usecase/getregularschedule.usecase";
import { ServerOrClientEnum } from "@/enum/serverOrClient.enum";
import { ExpectedError } from "@/Error/ExpectedError";
import { WorkTypeDTO } from "@/type/dto/worktype.dto";
import { Modalform } from "./modalform";
import { PostOrPut } from "@/enum/PostOrPut.enum";
import { PostRegularScheduleForm } from "@/type/form/postregularschedule.form";
import { DayOfWeek } from "@/enum/dayofweek.enum";
import { DeleteRegularScheduleUsecase } from "@/usecase/deleteregularschedule.usecase";
import { PutRegularScheduleUsecase } from "@/usecase/putregularschedule.usecase";
import { PutRegularScheduleForm } from "@/type/form/putregularschedule.form";
import { PostRegularScheduleUsecase } from "@/usecase/postregularschedu.usecase";
import Modal from "./modal";

type Props = {
    regularscheduleDTOList: RegularscheduleDTO[];
    worktypeDTOList: WorkTypeDTO[];
    from: string;
    to: string;
};

export default function RegularPage({ regularscheduleDTOList, worktypeDTOList, from, to }: Props) {

  const [regularscheduleDTOListState, setRegularscheduleDTOListState] = useState<RegularscheduleDTO[]>(regularscheduleDTOList);

  const [fromState, setFrom] = useState<string>(from);
  const [toState, setTo] = useState<string>(to);

  const [modalOpen, setModalOpen] = useState<boolean>(false);
  
  const [modalform, setModalform] = useState<Modalform>({
    startDate: '',
    endDate: '',
    startTime: '',
    endTime: '',
    dayOfWeek: undefined as any,
    workTypeId: ''
  });

  const [postOrPutState, setPostOrPutState] = useState<PostOrPut>();

  const [postRegularScheduleForm, setPostRegularScheduleForm] = useState<PostRegularScheduleForm>({
    startDate: '',
    endDate: '',
    startTime: '',
    endTime: '',
    dayOfWeek: undefined as any,
    workTypeId: ''
  });
    

  const handleSearch = async () => {
    const getSingleScheduleForm = {
      from: fromState,
      to: toState,
    };

    try {
      const regularscheduleDTOListFiltered: RegularscheduleDTO[] = await GetRegularScheduleUsecase(getSingleScheduleForm, ServerOrClientEnum.CLIENT);
      setRegularscheduleDTOListState(regularscheduleDTOListFiltered);
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
      const id = await DeleteRegularScheduleUsecase(scheduleId, ServerOrClientEnum.CLIENT);
      const updatedList = regularscheduleDTOListState.filter(schedule => schedule.scheduleId !== id);
      setRegularscheduleDTOListState(updatedList);
    } catch (error) {
 if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');}
      return;
    }
  }

  const handleUpdate = async (putregularscheduleform: PutRegularScheduleForm) => {
    try {
      await PutRegularScheduleUsecase(putregularscheduleform, ServerOrClientEnum.CLIENT);

      let worktypeName: string;
      let worktypeColor: string;

      worktypeDTOList.forEach((worktype) => {
        if (worktype.id === putregularscheduleform.workTypeId) {
          worktypeName = worktype.workTypeName;
          worktypeColor = worktype.workTypeColor;
        }
      });

      let updatedList: RegularscheduleDTO[] = [];

      regularscheduleDTOListState.forEach((schedule) => {
        if (schedule.scheduleId === putregularscheduleform.id) {
          const newSchedule = {
            scheduleId: putregularscheduleform.id,
            startTime: putregularscheduleform.startTime,
            endTime: putregularscheduleform.endTime,
            startDate: putregularscheduleform.startDate,
            endDate: putregularscheduleform.endDate,
            daysOfWeek: putregularscheduleform.dayOfWeek,
            worktypeName: worktypeName!,
            worktypeColor: worktypeColor!
          };
          updatedList.push(newSchedule);
        } else {
          updatedList.push(schedule);
        }
      });
      setRegularscheduleDTOListState(updatedList);
    } catch (error) {
 if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else 
        alert('An unexpected error occurred');} 
      return;
    }
  

  const handleCreate = async (postregularscheduleform: PostRegularScheduleForm) => {
    try {
      const id = await PostRegularScheduleUsecase(postregularscheduleform, ServerOrClientEnum.CLIENT);

      let worktypeName: string = '';
      let worktypeColor: string = '';

      worktypeDTOList.forEach((worktype) => {
        if (worktype.id === postregularscheduleform.workTypeId) {
          worktypeName = worktype.workTypeName;
          worktypeColor = worktype.workTypeColor;
        }
      });

      const newSchedule: RegularscheduleDTO = {
        scheduleId: id,
        startTime: postregularscheduleform.startTime,
        endTime: postregularscheduleform.endTime,
        worktypeName: worktypeName!,
        worktypeColor: worktypeColor!,
        startDate: postregularscheduleform.startDate,
        endDate: postregularscheduleform.endDate,
        daysOfWeek: postregularscheduleform.dayOfWeek
      };

      setRegularscheduleDTOListState([...regularscheduleDTOListState, newSchedule]);
    } catch (error) {
 if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');}
      return;
    }
  }

  const openModal = (postOrPut: PostOrPut, modalform?: Modalform) => {
    let newform: Modalform;

    switch (postOrPut) {
      case PostOrPut.POST:
        newform = {
          startDate: '',
          endDate: '',
          startTime: '',
          endTime: '', 
          dayOfWeek: undefined as any,
          workTypeId: ''
        };
        break;
      case PostOrPut.PUT:
        newform = modalform!;
        break;
    }
    setModalform(newform);
    setPostOrPutState(postOrPut);
    setModalOpen(true);
  }

  const closeModal = () => {
    setModalOpen(false);
  }

  const findWorkTypeId = (worktypeName: string): string => {
    let workTypeId = '';
    worktypeDTOList.forEach((worktype) => {
      if (worktype.workTypeName === worktypeName) {
        workTypeId = worktype.id;
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
      {regularscheduleDTOListState.length === 0 && <p>期間内にレギュラースケジュールは登録されていません。</p>}
      {regularscheduleDTOListState.length > 0 &&
      regularscheduleDTOListState.map((regularscheduleDTO) => (
        <Schedule key={regularscheduleDTO.scheduleId} regularscheduleDTO={regularscheduleDTO} handleDelete={handleDelete} openModal={openModal} findWorkTypeId={findWorkTypeId} />
      ))}
      <button type="button" onClick={() => openModal(PostOrPut.POST)}>新規作成</button>
      {modalOpen && (
        <Modal postOrPut={postOrPutState} modalform={modalform} closeModal={closeModal} handleCreate={handleCreate} handleUpdate={handleUpdate} worktypeDTOList={worktypeDTOList} />
      )}
    </>
  );
}
