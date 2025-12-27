import Loginpage from './loginpage';
import styles from './page.module.css';

export const dynamic = 'force-static';

/**
 * ログインページコンポーネント
 * @returns ログインページのJSX要素
 */
export default function Login() {
  return (
    <div className={styles.container}>
      <h1 className={styles.title}>WSMSA</h1>
      <h2 className={styles.subtitle}>Login Page</h2>
      <Loginpage></Loginpage>
    </div>
  );
}
