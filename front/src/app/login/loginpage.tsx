'use client';

import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { ExpectedError } from '@/Error/ExpectedError';
import { Loginform } from '@/type/form/login.form';
import { loginUsecase } from '@/usecase/login.usecase';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';

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
      await loginUsecase(form,ServerOrClientEnum.CLIENT);
    } catch (error) {
 if (error instanceof ExpectedError) {
        alert(error.messages.join('\n'));
      } else {
        alert('An unexpected error occurred');}
      return;
    }
    router.push('/search');
  };

  return (
    <form>
      <label htmlFor="username">Username</label>
      <input
        type="text"
        id="username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <label htmlFor="password">Password</label>
      <input
        type="password"
        id="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button type="button" onClick={onLoginClick}>
        Login
      </button>
    </form>
  );
}
