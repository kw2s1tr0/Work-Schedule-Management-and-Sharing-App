import { MethodEnum } from '@/enum/method.enum';

export async function clientFetch(
  url: string,
  method: MethodEnum,
  body?: unknown,
): Promise<Response> {
  const response = await fetch(url, {
    method: method,
    body: body ? JSON.stringify(body) : undefined,
    headers: body ? { 'Content-Type': 'application/json' } : undefined,
  });
  return response;
}
