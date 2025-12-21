import Link from 'next/link';

export default function SearchLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <>
      <aside>
        <ul>
          <li>
            <Link href="/edit/calendar">カレンダー</Link>
          </li>
          <li>
            <Link href="/edit/default">基本予定</Link>
          </li>
          <li>
            <Link href="/edit/regular">定期予定</Link>
          </li>
          <li>
            <Link href="/edit/irregular">非定期予定</Link>
          </li>
        </ul>
      </aside>
      {children}
    </>
  );
}
