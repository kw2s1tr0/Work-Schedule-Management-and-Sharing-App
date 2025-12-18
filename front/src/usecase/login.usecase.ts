import { Loginform } from "@/type/form/login.form";
import { Loginreq } from "@/type/req/login.req";
import { ExpectedError } from "@/Error/ExpectedError";

export async function loginUsecase(loginform: Loginform): Promise<void> {
    const loginreq: Loginreq = toreq(loginform);
    await post(loginreq);
}

function toreq (loginform: Loginform): Loginreq {
    const loginreq: Loginreq = {
        userId: loginform.userId,
        password: loginform.password
    };
    return loginreq;
}

async function post (loginreq: Loginreq): Promise<void> {
    const response = await fetch("/api/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(loginreq)
    })

    const data = await response.json();

    if (response.status !== 200) {
        throw new ExpectedError(response.status, [data.message]);
    }

    return;
}