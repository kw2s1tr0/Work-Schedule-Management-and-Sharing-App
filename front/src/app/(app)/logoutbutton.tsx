'use client';

import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { logoutUsecase } from '@/usecase/logout.usecase';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
import styles from './logoutbutton.module.css';


/**
 * ログアウトボタンコンポーネント
 * @returns ログアウトボタンコンポーネント
 */
export default function LogoutButton() {
  const router = useRouter();

  const [errorMessage, setErrorMessage] = useState<string>('');

  // エラーメッセージが更新されたときにアラートを表示
  useEffect(() => {
    if (errorMessage) {
      alert(errorMessage);
    }
  }, [errorMessage]);

  // ログアウト処理
  const onLogoutClick = async () => {
    try {
      await logoutUsecase(ServerOrClientEnum.CLIENT);
    } catch (error) {
      if (error instanceof ExpectedError) {
        setErrorMessage(error.messages.join('\n'));
      } else {
        setErrorMessage('An unexpected error occurred');
      }
      return;
    }
    router.push('/search');
  };

  return (
    <ul className={styles.logoutList}>
      <li>
        <Link
          href="/login"
          className={styles.logoutLink}
          onClick={onLogoutClick}
        >
          ログアウト
        </Link>
      </li>
    </ul>
  );
}
