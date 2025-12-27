import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

/**
 * proxy ルーターからの認証制御
 * 
 * @param req 
 * @returns 
 */
export async function proxy(req: NextRequest) {
  const res = await fetch(`${process.env.BFF_BASE_URL ?? ''}/api/session`, {
    headers: {
      cookie: req.headers.get('cookie') ?? '',
    },
  });

  // 認証されていない場合のリダイレクト制御
  if (res.status !== 200 && req.nextUrl.pathname !== '/login') {
    return NextResponse.redirect(new URL('/login', req.url));
  }

  // 認証されている場合のリダイレクト制御
  if (res.status !== 200 && req.nextUrl.pathname === '/login') {
    return NextResponse.next();
  }

  // 認証されている場合のリダイレクト制御
  if (req.nextUrl.pathname === '/login') {
    return NextResponse.redirect(new URL('/', req.url));
  }

  // ルートパスへのアクセスは検索画面へリダイレクト
  if (req.nextUrl.pathname === '/') {
    return NextResponse.redirect(new URL('/search', req.url));
  }

  // その他のパスはそのまま通す
  return NextResponse.next();
}

// このミドルウェアを適用するパスを指定
export const config = {
  matcher: ['/edit/:path*', '/search/:path*', '/login', '/'],
};
