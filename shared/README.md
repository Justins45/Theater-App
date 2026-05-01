# Shared Vue UI components

Shared components between the `frontend` and `admin` apps. Components here must be presentational — they receive data via props and communicate via emits only. No store access, no router dependencies.

## Setup

From the repo root, workspaces and type generation are handled automatically:

```bash
npm install
```

## Adding a new component

1. Create `components/YourComponent.vue`
2. Export it from `index.ts`
3. Run the type build from the `shared/` directory:

```bash
npm run build:types
```

4. Import it in `frontend` or `admin` — no changes needed in those projects

## Notes

- `dist/` is gitignored — it is generated on `npm install` via the root `prepare` script
- `frontend` resolves components via `vite.config.ts` alias, types via `tsconfig.app.json` paths
- `admin` is wired the same way
- Re-run `build:types` any time you add or rename an export — without it TypeScript will error but the app will still run