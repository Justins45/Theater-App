// vitest.config.ts  (repo root)
import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vitest/config'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      // each workspace's src/ is aliased as @ — the root path resolves
      // relative to the workspace root set in vitest.workspace.ts
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  test: {
    globals: true,          // matches create-vue default
    environment: 'jsdom',
    clearMocks: true,
  },
})