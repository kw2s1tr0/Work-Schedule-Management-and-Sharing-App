export async function POST(request: Request) {
    const body = await request.json();

    const data = await fetch("http://localhost:8080/api/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })

    const result = await data.json();

    return Response.json(result, {
        status: data.status,
        headers: {
            "Content-Type": "application/json"
        }
    });
} 