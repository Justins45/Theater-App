// vitest.workspace.ts  (repo root)
import { defineWorkspace } from 'vitest/config'

export default defineWorkspace([
  {
    extends: './vitest.config.ts',
    test: {
      name: 'frontend',
      root: './frontend',
    },
  },
  {
    extends: './vitest.config.ts',
    test: {
      name: 'admin',
      root: './admin',
    },
  },
  {
    extends: './vitest.config.ts',
    test: {
      name: 'shared',
      root: './shared',
    },
  },
])