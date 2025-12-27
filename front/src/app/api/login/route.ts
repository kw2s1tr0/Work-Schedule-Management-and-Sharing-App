/**
 * ログインAPIのPOSTメソッドハンドラ
 * @param request リクエストオブジェクト
 * @returns レスポンスオブジェクト
 */
export async function POST(request: Request) {
  const body = await request.json();

  const params = new URLSearchParams();
  params.append('userId', body.userId);
  params.append('password', body.password);

  // ログインなどはフォームURLエンコード形式で送信する
  const data = await fetch(`${process.env.BACKEND_BASE_URL}/api/login`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: params.toString(),
  });

  const result = await data.json();

  const response = Response.json(result, {
    status: data.status,
  });

  // Set-Cookieヘッダーをレスポンスに追加する
  const cookies = data.headers.get('set-cookie');
  if (cookies) {
    response.headers.set('set-cookie', cookies);
  }

  return response;
}
