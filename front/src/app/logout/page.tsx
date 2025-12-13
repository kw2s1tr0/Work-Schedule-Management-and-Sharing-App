import Link from "next/link";

export const dynamic = 'force-static';

export default function Logout() {
    return (
        <>
            <h1>WSMSA</h1>
            <h2>Logout Page</h2>
            <p>既にログアウトしています。</p>
            <p>再度ログインする場合は、<Link href="/login">こちら</Link>をクリックしてください。</p>
        </>
    );
}