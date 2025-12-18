import { Loginform } from "@/type/form/login.form";
import { Loginreq } from "@/type/req/login.req";
import { ExpectedError } from "@/Error/ExpectedError";

export async function logoutUsecase(): Promise<void> {
    await get();
}

async function get(): Promise<void> {
    const response = await fetch("/api/logout", {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        },
    })

    const data = await response.json();

    if (response.status !== 200) {
        throw new ExpectedError(response.status, [data.message]);
    }

    return;
}