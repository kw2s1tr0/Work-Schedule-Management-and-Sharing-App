export async function GET(request: Request) {
  const data = await fetch(`${process.env.BACKEND_BASE_URL}/api/workTypes`, {
    method: 'GET',
    headers: {
      cookie: request.headers.get('cookie') ?? '',
    },
  });

  const result = await data.json();

  const response = Response.json(result, {
    status: data.status,
  });

  return response;
}
