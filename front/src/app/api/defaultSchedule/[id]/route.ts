export async function DELETE(request: Request, { params }: { params: { id: string } }) {
  const id = params.id;

  const data = await fetch(`${process.env.BACKEND_BASE_URL}/api/defaultSchedule` + '/' + id, {
    method: 'DELETE',
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