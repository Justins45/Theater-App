import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import vueDevTools from "vite-plugin-vue-devtools";
import VueRouter from "vue-router/vite";

// https://vite.dev/config/
export default defineConfig({
  plugins: [VueRouter({}), vue(), vueDevTools()],
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `
          @use "@/assets/_variables.scss" as *;
        `,
      },
    },
  },
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
      "@theater/shared": fileURLToPath(new URL("../shared/index.ts", import.meta.url)),
      "@stores": fileURLToPath(new URL("./src/stores", import.meta.url)),
      "@components": fileURLToPath(new URL("./src/components", import.meta.url)),
      "@composable": fileURLToPath(new URL("./src/composable", import.meta.url)),
      "@api": fileURLToPath(new URL("./src/api/axios.ts", import.meta.url)),
    },
    // ensures symlinked packages resolve correctly
    preserveSymlinks: true,
  },
  optimizeDeps: {
    // tells Vite to pre-bundle the shared package too
    include: ["@theater/shared"],
  },
});
