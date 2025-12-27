'use client';

import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { Loginform } from '@/type/form/login.form';
import { loginUsecase } from '@/usecase/login.usecase';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import styles from './loginpage.module.css';

export default function Loginpage() {
  const router = useRouter();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const onLoginClick = async () => {
    const form: Loginform = {
      userId: username,
      password: password,
    };
    try {
      await loginUsecase(form, ServerOrClientEnum.CLIENT);
    } catch (error) {
      if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');
      }
      return;
    }
    router.push('/search');
  };

  return (
    <form className={styles.form}>
      <div className={styles.formGroup}>
        <label htmlFor="username" className={styles.label}>
          Username
        </label>
        <input
          type="text"
          id="username"
          className={styles.input}
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
      </div>
      <div className={styles.formGroup}>
        <label htmlFor="password" className={styles.label}>
          Password
        </label>
        <input
          type="password"
          id="password"
          className={styles.input}
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>
      <button type="button" className={styles.button} onClick={onLoginClick}>
        Login
      </button>
    </form>
  );
}
