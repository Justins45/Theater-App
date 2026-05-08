// eslint.config.ts  (repo root)
import { globalIgnores } from "eslint/config";
import {
  defineConfigWithVueTs,
  vueTsConfigs,
} from "@vue/eslint-config-typescript";
import pluginVue from "eslint-plugin-vue";
import pluginVitest from "@vitest/eslint-plugin";
import skipFormatting from "@vue/eslint-config-prettier/skip-formatting";

export default defineConfigWithVueTs(
  {
    name: "app/files-to-lint",
    files: [
      "frontend/src/**/*.{ts,mts,tsx,vue}",
      "admin/src/**/*.{ts,mts,tsx,vue}",
      "shared/**/*.{ts,mts,tsx,vue}",
    ],
  },

  globalIgnores([
    // build outputs
    "*/dist/**",
    "*/dist-ssr/**",
    // Tauri
    "admin/src-tauri/**",
    // coverage
    "*/coverage/**",
    // Vite generated — not user code
    "**/vite-env.d.ts",
  ]),

  pluginVue.configs["flat/essential"],
  vueTsConfigs.recommended,

  {
    ...pluginVitest.configs.recommended,
    files: [
      "frontend/src/**/__tests__/**/*.ts",
      "admin/src/**/__tests__/**/*.ts",
      "shared/**/__tests__/**/*.ts",
      "**/*.spec.ts",
      "**/*.test.ts",
    ],
  },

  {
    files: ["**/*.{vue,ts,mts,tsx}"],
    rules: {
      "vue/multi-word-component-names": "off",
    },
  },

  skipFormatting,
);
