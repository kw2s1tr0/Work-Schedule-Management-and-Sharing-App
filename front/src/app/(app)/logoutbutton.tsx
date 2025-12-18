"use client";

import { ExpectedError } from "@/Error/ExpectedError";
import { logoutUsecase } from "@/usecase/logout.usecase";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function LogoutButton() {
    const router = useRouter();

    const [errorMessage, setErrorMessage] = useState<string>("");

    useEffect(() => {
        if (errorMessage) {
            alert(errorMessage);
        }
    }, [errorMessage]);

    const onLogoutClick = async() => {
        try {
            await logoutUsecase();
        } catch (error) {
            if (error instanceof ExpectedError) {
                setErrorMessage(error.messages.join("\n"));
            }else {
                setErrorMessage("An unexpected error occurred");
            }
            return;
        }
        router.push("/search");
    }

    return (
        <ul>
            <li><Link href="/login" onClick={onLogoutClick}>ログアウト</Link></li>
        </ul>
    );
}