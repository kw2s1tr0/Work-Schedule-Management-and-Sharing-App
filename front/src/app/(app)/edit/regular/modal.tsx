import { WorkTypeDTO } from "@/type/dto/worktype.dto";
import { PostDefaultScheduleForm } from "@/type/form/postdefaultschedule.form";
import { useEffect, useState } from "react";
import { Modalform } from "./modalform";
import { PostOrPut } from "@/enum/PostOrPut.enum";
import { PutDefaultScheduleForm } from "@/type/form/putdefaultschedule.form";
import { ExpectedError } from "@/Error/ExpectedError";
import { PutRegularScheduleForm } from "@/type/form/putregularschedule.form";
import { PostRegularScheduleForm } from "@/type/form/postregularschedule.form";
import { DayOfWeek } from "@/enum/dayofweek.enum";

type Props = {
    worktypeDTOList: WorkTypeDTO[];
    modalform: Modalform;
    postOrPut?: PostOrPut;
    handleUpdate: (putRegularScheduleForm: PutRegularScheduleForm) => Promise<void>;
    handleCreate: (postRegularScheduleForm: PostRegularScheduleForm) => Promise<void>;
    closeModal: () => void;
};

export default function Modal({worktypeDTOList, modalform, postOrPut, handleUpdate, handleCreate, closeModal}: Props) {

    const {id, startDate, endDate, startTime, endTime, dayOfWeek, workTypeId} = modalform;

    const [startTimestate, setStartTime] = useState<string>("");
    const [endTimestate, setEndTime] = useState<string>("");
    const [startDatestate, setStartDate] = useState<string>("");
    const [endDatestate, setEndDate] = useState<string>("");
    const [workTypeIdstate, setWorkTypeId] = useState<string>("");
    const [dayOfWeekState, setDayOfWeek] = useState<DayOfWeek | "">("");
    const [workTypeColorState, setWorkTypeColorState] = useState<string>("");

    useEffect(() => {
        setStartTime(startTime);
        setEndTime(endTime);
        setStartDate(startDate);
        setEndDate(endDate);
        setWorkTypeId(workTypeId);  
        setDayOfWeek(dayOfWeek);
        handleWorkTypeChange(workTypeId);
    }, [startTime, endTime, startDate, endDate, dayOfWeek, workTypeId]);



    const handleWorkTypeChange = (id: string) => {
        if (!id) {
            setWorkTypeColorState("");
            return;
        }
        const selectedWorkType = worktypeDTOList.find((worktype) => worktype.id === id);
        if (selectedWorkType) {
            setWorkTypeColorState(selectedWorkType.workTypeColor);
        }
    };

    const handleScheduleUpdate = async () => {
        if (!id) return;
        const putRegularScheduleForm: PutRegularScheduleForm = {
            id: id,
            startDate: startDatestate,
            endDate: endDatestate,
            startTime: startTimestate,
            endTime: endTimestate,
            dayOfWeek: dayOfWeekState as DayOfWeek,
            workTypeId: workTypeIdstate
        };
        try {
            await handleUpdate(putRegularScheduleForm);
            closeModal();
        } catch (error) {
            if (error instanceof ExpectedError) {
                alert(error.messages.join('\n'));
            } else {
                alert('An unexpected error occurred');
            }
        }
    }

    const handleScheduleCreate = async () => {
        const postRegularScheduleForm: PostRegularScheduleForm = {
            startDate: startDatestate,
            endDate: endDatestate,
            startTime: startTimestate,
            endTime: endTimestate,
            dayOfWeek: dayOfWeekState as DayOfWeek,
            workTypeId: workTypeIdstate
        };
        try {
            await handleCreate(postRegularScheduleForm);
            closeModal();
        } catch (error) {
            if (error instanceof ExpectedError) {
                alert(error.messages.join('\n'));
            } else {
                alert('An unexpected error occurred');
            }
        }
    }

    return (
        <div>
            {postOrPut== PostOrPut.PUT && <p>{id}</p>}
            <label htmlFor="startTime">開始時間:</label>
            <input type="time" id="startTime" name="startTime" value={startTimestate} onChange={(e) => setStartTime(e.target.value)} />
            <label htmlFor="endTime">終了時間:</label>
            <input type="time" id="endTime" name="endTime" value={endTimestate} onChange={(e) => setEndTime(e.target.value)} />
            <label htmlFor="startDate">開始日:</label>
            <input type="date" id="startDate" name="startDate" value={startDatestate} onChange={(e) => setStartDate(e.target.value)}  />
            <label htmlFor="endDate">終了日:</label>
            <input type="date" id="endDate" name="endDate" value={endDatestate} onChange={(e) => setEndDate(e.target.value)} />
            <label style={{backgroundColor: workTypeColorState}} htmlFor="workTypeId">勤務タイプ:</label>
            <select id="workTypeId" name="workTypeId" value={workTypeIdstate} onChange={(e) => {
                setWorkTypeId(e.target.value);
                handleWorkTypeChange(e.target.value);
            }}>
                <option value="">選択してください</option>
                {worktypeDTOList.map((worktype) => (
                    <option key={worktype.id} value={worktype.id}>
                        {worktype.workTypeName}
                    </option>
                ))}
            </select>
            <label htmlFor="dayOfWeek">曜日:</label>
            <select id="dayOfWeek" name="dayOfWeek" value={dayOfWeekState} onChange={(e) => setDayOfWeek(e.target.value as DayOfWeek)}>
                <option value="">選択してください</option>
                <option value={DayOfWeek.MONDAY}>月曜日</option>
                <option value={DayOfWeek.TUESDAY}>火曜日</option>
                <option value={DayOfWeek.WEDNESDAY}>水曜日</option>
                <option value={DayOfWeek.THURSDAY}>木曜日</option>
                <option value={DayOfWeek.FRIDAY}>金曜日</option>
                <option value={DayOfWeek.SATURDAY}>土曜日</option>
                <option value={DayOfWeek.SUNDAY}>日曜日</option>
            </select>
            {postOrPut== PostOrPut.PUT && <button type="button" onClick={handleScheduleUpdate}>更新</button>}
            {postOrPut== PostOrPut.POST && <button type="button" onClick={handleScheduleCreate}>作成</button>}
            <button type="button" onClick={closeModal}>閉じる</button>
        </div>
    )
}