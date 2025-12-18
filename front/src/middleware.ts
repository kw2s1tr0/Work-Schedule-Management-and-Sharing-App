import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

export async function middleware(req: NextRequest) {
  const res = await fetch("http://localhost:3000/api/session", {
    headers: {
      cookie: req.headers.get("cookie") ?? "",
    },
  });

  if (res.status !== 200 && req.nextUrl.pathname !== "/login") {
    return NextResponse.redirect(new URL("/login", req.url));
  }

  if (res.status !== 200 && req.nextUrl.pathname === "/login") {
    return NextResponse.next();
  }

  if (req.nextUrl.pathname === "/login") {
    return NextResponse.redirect(new URL("/", req.url));
  }

  if (req.nextUrl.pathname === "/") {
    return NextResponse.redirect(new URL("/search", req.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: [
    "/edit/:path*",
    "/search/:path*",
    "/login",
    "/",
  ],
};
