import { MethodEnum } from '@/enum/method.enum';
import { ServerOrClientEnum } from '@/enum/serverOrClient.enum';
import { serverFetch } from './server-fetch';
import { clientFetch } from './client-fetch';

export async function fetcher(
  url: string,
  method: MethodEnum,
  type: ServerOrClientEnum,
  body?: unknown,
  cookie?: string,
): Promise<Response> {
  let response;
  if (type === ServerOrClientEnum.SERVER) {
    response = await serverFetch(url, method, body, cookie);
  } else {
    response = await clientFetch(url, method, body);
  }
  return response;
}
