import Link from 'next/link';
import styles from './not-found.module.css';

/**
 * 404 Not Foundページコンポーネント
 * @returns 404ページのJSX要素
 */
export default function NotFound() {
  return (
    <div className={styles.container}>
      <h1 className={styles.title}>404</h1>
      <p className={styles.message}>ページが見つかりません</p>
      <Link href="/" className={styles.link}>
        ホームへ戻る
      </Link>
    </div>
  );
}
