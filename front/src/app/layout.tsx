import type { Metadata } from 'next';
import { Geist, Geist_Mono } from 'next/font/google';
import styles from './layout.module.css';

// Geistフォントの設定
const geistSans = Geist({
  variable: '--font-geist-sans',
  subsets: ['latin'],
});

// Geist Monoフォントの設定
const geistMono = Geist_Mono({
  variable: '--font-geist-mono',
  subsets: ['latin'],
});

// メタデータの設定
export const metadata: Metadata = {
  title: 'WSMSA',
  description: 'WSMSA Application',
};

// ビューポートの設定
export const viewport = {
  width: 'device-width',
  initialScale: 1,
};

/**
 * ルートレイアウトコンポーネント
 * @param param0 子要素
 * @returns ルートレイアウトのJSX要素
 */
export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ja">
      <body className={`${geistSans.variable} ${geistMono.variable}`}>
        <div className={styles.container}>
          <main className={styles.main}>{children}</main>
          <footer className={styles.footer}>
            <p>© 2025 WSMSA. All rights reserved.</p>
          </footer>
        </div>
      </body>
    </html>
  );
}
