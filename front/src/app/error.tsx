'use client';

import styles from './error.module.css';

export default function Error({
  error,
  reset,
}: {
  error: Error;
  reset: () => void;
}) {
  return (
    <div className={styles.container}>
      <h2 className={styles.title}>エラーが発生しました</h2>
      <p className={styles.message}>{error.message}</p>
      <button className={styles.button} onClick={reset}>
        再試行
      </button>
    </div>
  );
}
