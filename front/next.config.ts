import { watch } from "fs";
import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  webpack: (config, {isServer}) => {
    if (!isServer) {
      config.watchOptions = {
        poll: 1000,
      };
    }
    return config;
  }
  /* config options here */
};

export default nextConfig;
