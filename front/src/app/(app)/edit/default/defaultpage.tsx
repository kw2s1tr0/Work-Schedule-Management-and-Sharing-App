'use client';

import { DefaultScheduleDTO } from '@/type/dto/defaultschedule.dto';
import Schedule from './schedule';
import { useState } from 'react';
import { GetDefaultScheduleUsecase } from '@/usecase/getdefaultschedule.usecase';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { DeleteDefaultScheduleUsecase } from '@/usecase/deletedefaultschedule.usecase';
import { PutDefaultScheduleForm } from '@/type/form/putdefaultschedule.form';
import { PutDefaultScheduleUsecase } from '@/usecase/putdefaultschedule.usecase';
import { WorkTypeDTO } from '@/type/dto/worktype.dto';
import { PostDefaultScheduleUsecase } from '@/usecase/postdefaultschedule.usecase';
import { PostDefaultScheduleForm } from '@/type/form/postdefaultschedule.form';
import Modal from './modal';
import { PostOrPut } from '@/enum/PostOrPut.enum';
import { Modalform } from './modalform';
import styles from './defaultpage.module.css';

type Props = {
  defaultscheduleDTOList: DefaultScheduleDTO[];
  worktypeDTOList: WorkTypeDTO[];
  from: string;
  to: string;
};

export default function DefaultPage({
  defaultscheduleDTOList,
  worktypeDTOList,
  from,
  to,
}: Props) {
  const [defaultscheduleDTOListState, setDefaultscheduleDTOListState] =
    useState<DefaultScheduleDTO[]>(defaultscheduleDTOList);

  const [fromState, setFrom] = useState<string>(from);
  const [toState, setTo] = useState<string>(to);

  const [modalOpen, setModalOpen] = useState<boolean>(false);

  const [modalform, setModalform] = useState<Modalform>({
    startDate: '',
    endDate: '',
    startTime: '',
    endTime: '',
    workTypeId: '',
  });

  const [postOrPutState, setPostOrPutState] = useState<PostOrPut>();

  const handleSearch = async () => {
    const getSingleScheduleForm = {
      from: fromState,
      to: toState,
    };

    try {
      const defaultscheduleDTOListFiltered: DefaultScheduleDTO[] =
        await GetDefaultScheduleUsecase(
          getSingleScheduleForm,
          ServerOrClientEnum.CLIENT,
        );
      setDefaultscheduleDTOListState(defaultscheduleDTOListFiltered);
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
      return;
    }
  };

  const handleDelete = async (scheduleId: string) => {
    try {
      const id = await DeleteDefaultScheduleUsecase(
        scheduleId,
        ServerOrClientEnum.CLIENT,
      );
      const updatedList = defaultscheduleDTOListState.filter(
        (schedule) => schedule.scheduleId !== id,
      );
      setDefaultscheduleDTOListState(updatedList);
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
      return;
    }
  };

  const handleUpdate = async (
    putdefaultscheduleform: PutDefaultScheduleForm,
  ) => {
    try {
      await PutDefaultScheduleUsecase(
        putdefaultscheduleform,
        ServerOrClientEnum.CLIENT,
      );

      let worktypeName: string;
      let worktypeColor: string;
      worktypeDTOList.forEach((worktype) => {
        if (worktype.id === putdefaultscheduleform.workTypeId) {
          worktypeName = worktype.workTypeName;
          worktypeColor = worktype.workTypeColor;
        }
      });

      const updatedList: DefaultScheduleDTO[] = [];

      defaultscheduleDTOListState.forEach((schedule) => {
        if (schedule.scheduleId === putdefaultscheduleform.id) {
          const newSchedule = {
            scheduleId: putdefaultscheduleform.id,
            startTime: putdefaultscheduleform.startTime,
            endTime: putdefaultscheduleform.endTime,
            startDate: putdefaultscheduleform.startDate,
            endDate: putdefaultscheduleform.endDate,
            worktypeName: worktypeName!,
            worktypeColor: worktypeColor!,
          };
          updatedList.push(newSchedule);
        } else {
          updatedList.push(schedule);
        }
      });
      setDefaultscheduleDTOListState(updatedList);
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
      return;
    }
  };

  const handleCreate = async (
    postdefaultscheduleform: PostDefaultScheduleForm,
  ) => {
    try {
      const id = await PostDefaultScheduleUsecase(
        postdefaultscheduleform,
        ServerOrClientEnum.CLIENT,
      );

      let newworktypeName: string = '';
      let newworktypeColor: string = '';
      worktypeDTOList.forEach((worktype) => {
        if (worktype.id === postdefaultscheduleform.workTypeId) {
          newworktypeName = worktype.workTypeName;
          newworktypeColor = worktype.workTypeColor;
        }
      });

      const newSchedule: DefaultScheduleDTO = {
        scheduleId: id,
        startTime: postdefaultscheduleform.startTime,
        endTime: postdefaultscheduleform.endTime,
        worktypeName: newworktypeName,
        worktypeColor: newworktypeColor,
        startDate: postdefaultscheduleform.startDate,
        endDate: postdefaultscheduleform.endDate,
      };

      setDefaultscheduleDTOListState([
        ...defaultscheduleDTOListState,
        newSchedule,
      ]);
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
      return;
    }
  };

  const openModal = (postOrPut: PostOrPut, modalform?: Modalform) => {
    let newModalform: Modalform;
    switch (postOrPut) {
      case PostOrPut.POST:
        newModalform = {
          startDate: '',
          endDate: '',
          startTime: '',
          endTime: '',
          workTypeId: '',
        };
        break;
      case PostOrPut.PUT:
        newModalform = modalform!;
        break;
    }
    setPostOrPutState(postOrPut);
    setModalform(newModalform);
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  const findWorkTypeId = (worktypeName: string): string => {
    let workTypeId = '';
    worktypeDTOList.forEach((worktype) => {
      if (worktype.workTypeName === worktypeName) {
        workTypeId = worktype.id;
      }
    });
    return workTypeId;
  };

  return (
    <>
      <form className={styles.form}>
        <div className={styles.formGroup}>
          <label htmlFor="from" className={styles.label}>
            開始日
          </label>
          <input
            type="date"
            id="from"
            name="from"
            className={styles.input}
            value={fromState}
            onChange={(e) => setFrom(e.target.value)}
          />
        </div>
        <div className={styles.formGroup}>
          <label htmlFor="to" className={styles.label}>
            終了日
          </label>
          <input
            type="date"
            id="to"
            name="to"
            className={styles.input}
            value={toState}
            onChange={(e) => setTo(e.target.value)}
          />
        </div>
        <button
          type="button"
          className={styles.searchButton}
          onClick={handleSearch}
        >
          検索
        </button>
      </form>
      {defaultscheduleDTOListState.length === 0 && (
        <p className={styles.noResults}>
          期間内にデフォルトスケジュールは登録されていません。
        </p>
      )}
      {defaultscheduleDTOListState.length > 0 && (
        <div className={styles.scheduleList}>
          {defaultscheduleDTOListState.map((defaultscheduleDTO) => (
            <Schedule
              key={defaultscheduleDTO.scheduleId}
              defaultscheduleDTO={defaultscheduleDTO}
              handleDelete={handleDelete}
              openModal={openModal}
              findWorkTypeId={findWorkTypeId}
            />
          ))}
        </div>
      )}
      <button
        type="button"
        className={styles.createButton}
        onClick={() => openModal(PostOrPut.POST)}
      >
        新規作成
      </button>
      {modalOpen && (
        <Modal
          worktypeDTOList={worktypeDTOList}
          modalform={modalform}
          postOrPut={postOrPutState}
          handleUpdate={handleUpdate}
          handleCreate={handleCreate}
          closeModal={closeModal}
        />
      )}
    </>
  );
}
