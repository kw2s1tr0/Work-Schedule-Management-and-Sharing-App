/**
 * ユーザー情報取得APIのGETメソッドハンドラ
 * @param request リクエストオブジェクト
 * @returns レスポンスオブジェクト
 */
export async function GET(request: Request) {
  const data = await fetch(`${process.env.BACKEND_BASE_URL}/api/me`, {
    method: 'GET',
    headers: {
      cookie: request.headers.get('cookie') ?? '',
    },
  });

  const body = await data.text();

  return new Response(body, {
    status: data.status,
    headers: {
      'Content-Type': 'text/plain',
    },
  });
}
