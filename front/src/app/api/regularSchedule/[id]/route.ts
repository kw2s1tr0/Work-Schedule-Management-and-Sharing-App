/**
 * 定期スケジュール削除APIのDELETEメソッドハンドラ
 * @param request リクエストオブジェクト
 * @param params パスパラメータオブジェクト
 * @returns レスポンスオブジェクト
 */
export async function DELETE(
  request: Request,
  { params }: { params: Promise<{ id: string }> },
) {
  const { id } = await params;

  const data = await fetch(
    `${process.env.BACKEND_BASE_URL}/api/regularSchedule` + '/' + id,
    {
      method: 'DELETE',
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
