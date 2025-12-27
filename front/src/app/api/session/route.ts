/**
 * セッション情報取得APIのGETメソッドハンドラ
 * @param request リクエストオブジェクト
 * @returns レスポンスオブジェクト
 */
export async function GET(request: Request) {
  const data = await fetch(`${process.env.BACKEND_BASE_URL}/api/session`, {
    method: 'GET',
    headers: {
      cookie: request.headers.get('cookie') ?? '',
    },
  });

  return Response.json(null, {
    status: data.status,
    headers: {
      'Content-Type': 'application/json',
    },
  });
}
