import Link from 'next/link';
import styles from './layout.module.css';

/**
 * 編集ページレイアウトコンポーネント
 * @param children 子コンポーネント
 * @returns 編集ページレイアウトコンポーネント
 */
export default function SearchLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <div className={styles.container}>
      <aside className={styles.aside}>
        <ul className={styles.navList}>
          <li>
            <Link href="/edit/calendar" className={styles.navLink}>
              カレンダー
            </Link>
          </li>
          <li>
            <Link href="/edit/default" className={styles.navLink}>
              基本予定
            </Link>
          </li>
          <li>
            <Link href="/edit/regular" className={styles.navLink}>
              定期予定
            </Link>
          </li>
          <li>
            <Link href="/edit/irregular" className={styles.navLink}>
              非定期予定
            </Link>
          </li>
        </ul>
      </aside>
      <div className={styles.content}>{children}</div>
    </div>
  );
}
