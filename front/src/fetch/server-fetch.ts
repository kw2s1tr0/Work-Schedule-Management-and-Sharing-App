import { MethodEnum } from '@/enum/method.enum';

export async function serverFetch(
  url: string,
  method: MethodEnum,
  body?: unknown,
  cookie?: string,
): Promise<Response> {
  url = process.env.BFF_BASE_URL + url;
  const response = await fetch(url, {
    method: method,
    headers: {
      cookie: cookie ?? '',
      ...(body ? { 'Content-Type': 'application/json' } : {}),
    },
    body: body ? JSON.stringify(body) : undefined,
  });
  return response;
}
