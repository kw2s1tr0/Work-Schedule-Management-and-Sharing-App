import { Loginform } from "@/type/form/login.form";
import { Loginreq } from "@/type/req/login.req";
import { Loginres } from "@/type/res/login.res";
import { ExpectedError } from "@/Error/LoginError";

export async function loginUsecase(loginform: Loginform): Promise<void> {
    const loginreq: Loginreq = toreq(loginform);
    const loginres: Loginres = await post(loginreq);
    throwIfError(loginres);
}

function toreq (loginform: Loginform): Loginreq {
    const loginreq: Loginreq = {
        userId: loginform.userId,
        password: loginform.password
    };
    return loginreq;
}

async function post (loginreq: Loginreq): Promise<Loginres> {
    const response = await fetch("/api/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(loginreq)
    })

    const data = await response.json();
    const status: number = response.status;

    const loginres: Loginres = {
        status: status,
        data: data
    };

    return loginres;
}

function throwIfError (loginres: Loginres): void {
    if (loginres.status !== 200) {
        throw new ExpectedError(loginres.status, [loginres.data.message]);
    }
}