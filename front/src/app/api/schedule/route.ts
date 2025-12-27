export async function GET(request: Request) {
  const { searchParams } = new URL(request.url);

  const params = new URLSearchParams();
  if (searchParams.get('userId')) {
    params.append('userId', searchParams.get('userId') ?? '');
  }
  if (searchParams.get('week')) {
    params.append('week', searchParams.get('week') ?? '');
  }
  if (searchParams.get('month')) {
    params.append('month', searchParams.get('month') ?? '');
  }
  if (searchParams.get('name')) {
    params.append('name', searchParams.get('name') ?? '');
  }
  if (searchParams.get('organizationCode')) {
    params.append(
      'organizationCode',
      searchParams.get('organizationCode') ?? '',
    );
  }
  if (searchParams.get('viewMode')) {
    params.append('viewMode', searchParams.get('viewMode') ?? '');
  }

  const data = await fetch(
    `${process.env.BACKEND_BASE_URL}/api/schedule` + '?' + params.toString(),
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
