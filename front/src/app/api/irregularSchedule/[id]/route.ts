export async function DELETE(request: Request, { params }: { params: Promise<{ id: string }> }) {
  const {id} = await params;

  const data = await fetch(`${process.env.BACKEND_BASE_URL ?? ''}/api/irregularSchedule` + '/' + id, {
    method: 'DELETE',
    headers: {
      cookie: request.headers.get('cookie') ?? '',
    },
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