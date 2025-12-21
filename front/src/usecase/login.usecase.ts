import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { Loginform } from '@/type/form/login.form';
import { Loginreq } from '@/type/req/login.req';
import { ExpectedError } from '@/Error/ExpectedError';
import { fetcher } from '@/fetch/fetch';

export async function loginUsecase(loginform: Loginform, type: ServerOrClientEnum): Promise<void> {
  const loginreq: Loginreq = toreq(loginform);
  await post(loginreq, type);
}

function toreq(loginform: Loginform): Loginreq {
  const loginreq: Loginreq = {
    userId: loginform.userId,
    password: loginform.password,
  };
  return loginreq;
}

async function post(loginreq: Loginreq, type: ServerOrClientEnum): Promise<void> {
  const response = await fetcher(`/api/login`, MethodEnum.POST, type, loginreq);

  const data = await response.json();

  if (!(200 <= response.status && response.status < 300)) {
    throw new ExpectedError(response.status, [data.message]);
  }

  return;
}
