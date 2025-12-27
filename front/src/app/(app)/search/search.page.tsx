'use client';

import { UserDTO } from '@/type/dto/user.dto';
import Day from './day';
import { useState } from 'react';
import { OrganizationDTO } from '@/type/dto/organization.dto';
import { GetScheduleUsecase } from '@/usecase/getschedule.usecase';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import styles from './search.page.module.css';

type Props = {
  userDTOlist: UserDTO[];
  organizationDTOlist: OrganizationDTO[];
  week: string;
};

/**
 * 検索ページコンポーネント
 * @param userDTOlist ユーザーデータ転送オブジェクトリスト
 * @param organizationDTOlist 組織データ転送オブジェクトリスト
 * @param week 週
 * @returns 検索ページコンポーネント
 */
export default function Searchpage({
  userDTOlist,
  organizationDTOlist,
  week,
}: Props) {
  const [userDTOlistState, setUserDTOlistState] =
    useState<UserDTO[]>(userDTOlist);
  const [userId, setUserId] = useState<string>('');
  const [weekState, setWeekState] = useState<string>(week);
  const [name, setName] = useState<string>('');
  const [organizationCode, setOrganizationCode] = useState<string>('');

  // 検索処理
  const handleSearch = async () => {
    const getScheduleForm = {
      userId: userId,
      week: weekState,
      month: '',
      name: name,
      organizationCode: organizationCode,
    };

    try {
      const UserDTOlistFiltered = await GetScheduleUsecase(
        getScheduleForm,
        ServerOrClientEnum.CLIENT,
      );

      setUserDTOlistState(UserDTOlistFiltered);
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
      return;
    }
  };

  return (
    <>
      <form className={styles.form}>
        <div className={styles.formGroup}>
          <label htmlFor="userId" className={styles.label}>
            ユーザーID
          </label>
          <input
            type="text"
            id="userId"
            name="userId"
            className={styles.input}
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
          />
        </div>
        <div className={styles.formGroup}>
          <label htmlFor="week" className={styles.label}>
            期間
          </label>
          <input
            type="week"
            id="week"
            name="week"
            className={styles.input}
            value={weekState}
            onChange={(e) => setWeekState(e.target.value)}
          />
        </div>
        <div className={styles.formGroup}>
          <label htmlFor="name" className={styles.label}>
            名前
          </label>
          <input
            type="text"
            id="name"
            name="name"
            className={styles.input}
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </div>
        <div className={styles.formGroup}>
          <label htmlFor="organizationCode" className={styles.label}>
            組織名
          </label>
          <select
            name="organizationCode"
            id="organizationCode"
            className={styles.select}
            value={organizationCode}
            onChange={(e) => setOrganizationCode(e.target.value)}
          >
            <option value="">選択してください</option>
            {organizationDTOlist.map((organizationDTO) => (
              <option
                key={organizationDTO.organizationCode}
                value={organizationDTO.organizationCode}
              >
                {organizationDTO.organizationName}
              </option>
            ))}
          </select>
        </div>
        <div className={styles.buttonContainer}>
          <button
            type="button"
            className={styles.searchButton}
            onClick={handleSearch}
          >
            Search
          </button>
        </div>
      </form>
      {userDTOlistState.length === 0 && (
        <div className={styles.noResults}>該当するデータはありません。</div>
      )}
      {userDTOlistState.length > 0 && (
        <>
          <p className={styles.resultsCount}>
            {userDTOlistState.length}件見つかりました。
          </p>
          <div className={styles.weekHeader}>
            <div></div>
            <div>月</div>
            <div>火</div>
            <div>水</div>
            <div>木</div>
            <div>金</div>
            <div>土</div>
            <div>日</div>
          </div>
          <div className={styles.result}>
            {userDTOlistState.map((userDTO) => (
              <div key={userDTO.userName} className={styles.userSchedule}>
                <div className={styles.userInfo}>
                  <p className={styles.organizationName}>
                    {userDTO.organizationName}
                  </p>
                  <p className={styles.userName}>{userDTO.userName}</p>
                </div>
                <div className={styles.schedules}>
                  {userDTO.schedules.map((scheduleDTO) => (
                    <Day key={scheduleDTO.date} scheduleDTO={scheduleDTO} />
                  ))}
                </div>
              </div>
            ))}
          </div>
        </>
      )}
    </>
  );
}
