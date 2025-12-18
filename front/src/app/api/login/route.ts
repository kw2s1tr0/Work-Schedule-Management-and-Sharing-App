export async function POST(request: Request) {
    const body = await request.json();

    const params = new URLSearchParams();
    params.append("userId", body.userId);
    params.append("password", body.password);

    const data = await fetch("http://host.docker.internal:8080/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: params.toString()
        })

    const result = await data.json();

    const response = Response.json(result, {
        status: data.status,
    });

    const cookies = data.headers.get("set-cookie");
    if (cookies) {
        response.headers.set("set-cookie", cookies);
    }

    return response;
}