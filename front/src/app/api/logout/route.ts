export async function GET(request: Request) {
  const data = await fetch(`${process.env.BACKEND_BASE_URL}/api/logout`, {
    method: 'GET',
    headers: {
      cookie: request.headers.get('cookie') ?? '',
    },
  });

  const result = await data.json();

  return Response.json(result, {
    status: data.status,
    headers: {
      'Content-Type': 'application/json',
    },
  });
}
