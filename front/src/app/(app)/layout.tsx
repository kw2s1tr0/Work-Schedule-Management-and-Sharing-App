import Link from 'next/link';
import LogoutButton from './logoutbutton';
import styles from './layout.module.css';

export default function ScheduleLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <>
      <header className={styles.header}>
        <div className={styles.headerContainer}>
          <h1 className={styles.title}>WSMSA</h1>
          <nav className={styles.nav}>
            <ul className={styles.navList}>
              <li>
                <Link href="/edit/calendar" className={styles.navLink}>
                  登録
                </Link>
              </li>
              <li>
                <Link href="/search" className={styles.navLink}>
                  検索
                </Link>
              </li>
            </ul>
            <LogoutButton></LogoutButton>
          </nav>
        </div>
      </header>
      <main className={styles.main}>{children}</main>
    </>
  );
}
