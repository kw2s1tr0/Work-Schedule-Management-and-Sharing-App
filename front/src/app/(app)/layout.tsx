import Link from "next/link";

export default function ScheduleLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <>
        <header>
            <h1>WSMSA</h1>
            <nav>
                <ul>
                    <li><Link href="/edit/default">登録</Link></li>
                    <li><Link href="/search">検索</Link></li>
                </ul>
                <ul>
                    <li><Link href="/logout">ログアウト</Link></li>
                </ul>
            </nav>
        </header>
            <main>
                {children}
            </main>
        </>
    );
}