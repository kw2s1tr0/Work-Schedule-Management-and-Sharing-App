/**
 * 組織情報取得APIのGETメソッドハンドラ
 * @param request リクエストオブジェクト
 * @returns レスポンスオブジェクト
 */
export async function GET(request: Request) {
  const data = await fetch(
    `${process.env.BACKEND_BASE_URL}/api/organizations`,
    {
      method: 'GET',
      headers: {
        cookie: request.headers.get('cookie') ?? '',
      },
    },
  );

  const result = await data.json();

  const response = Response.json(result, {
    status: data.status,
  });

  return response;
}
