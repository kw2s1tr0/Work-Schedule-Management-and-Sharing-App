export async function GET(request: Request) {
  const {searchParams} = new URL(request.url);

  const params = new URLSearchParams();

  if (searchParams.get('from')){
    params.append('from', searchParams.get('from') ?? '');
  }
  if (searchParams.get('to')){
    params.append('to', searchParams.get('to') ?? '');
  }

  const data = await fetch(`${process.env.BACKEND_BASE_URL}/api/regularSchedule` + '?' + params.toString(), {
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

export async function POST(request: Request) {
  const body = await request.json();

  const data = await fetch(`${process.env.BACKEND_BASE_URL}/api/regularSchedule`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      cookie: request.headers.get('cookie') ?? '',
    },
    body: JSON.stringify(body),
  });

  const result = await data.json();

  const response = Response.json(result, {
    status: data.status,
  });

  return response;
}

export async function PUT(request: Request) {
  const body = await request.json();

  const data = await fetch(`${process.env.BACKEND_BASE_URL}/api/regularSchedule`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      cookie: request.headers.get('cookie') ?? '',
    },
    body: JSON.stringify(body),
  });

  if (data.status === 204) {
    return new Response(null, { status: 204 });
  }

  const result = await data.json();

  const response = Response.json(result, {
    status: data.status,
  });

  return response;
}
