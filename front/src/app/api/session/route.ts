export async function GET(request: Request) {
    const data = await fetch("http://host.docker.internal:8080/api/session", {
        method: "GET",
        headers: {
            cookie: request.headers.get("cookie") ?? "",
        },
    });

    return Response.json(null, {
        status: data.status,
        headers: {
            "Content-Type": "application/json"
        }
    });
}   