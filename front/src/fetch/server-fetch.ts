import { MethodEnum } from '@/enum/method.enum';

/**
 * サーバーフェッチ関数
 * @param url - リクエストURL
 * @param method - HTTPメソッド
 * @param body - リクエストボディ（オプション）
 * @param cookie - クッキー（オプション）
 * @returns レスポンスオブジェクト
 */
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
