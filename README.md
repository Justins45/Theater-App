# 🎭 Theater App

A full-stack all-in-one theater ticketing platform intented to lower costs, and streamline management for theaters.

## Quick Links <!-- omit in toc -->

- [What It Does](#what-it-does)
- [The Three Flows](#the-three-flows)
- [Architecture Overview](#architecture-overview)
- [Backend — Spring Boot 4](#backend--spring-boot-4)
  - [Domain model](#domain-model)
  - [Cart system](#cart-system)
  - [Security](#security)
  - [API design](#api-design)
- [Frontend — Vue 3 (Patron App)](#frontend--vue-3-patron-app)
  - [Interactive SVG seating map](#interactive-svg-seating-map)
  - [Cart store](#cart-store)
  - [Patterns](#patterns)
- [Admin App — Tauri + Rust](#admin-app--tauri--rust)
- [Infrastructure \& Tooling](#infrastructure--tooling)
  - [CI/CD — GitHub Actions](#cicd--github-actions)
  - [Monorepo tooling](#monorepo-tooling)
  - [Observability (planned)](#observability-planned)
- [Tech Stack at a Glance](#tech-stack-at-a-glance)
- [What This Project Demonstrates](#what-this-project-demonstrates)
- [Roadmap](#roadmap)

## What It Does

Theater App is a complete ticketing system for live theater venues. It handles the full lifecycle of a ticket:

- **Patrons** browse events, select seats on an interactive map, add tickets to a cart, and check out.
- **Admins** manage venues, stages, events, performances, and seating through a native multi-platform desktop app.
- **Door staff** scan QR codes at the door to validate tickets in real time.

Three distinct user flows, three distinct interfaces — all sharing a single backend.

## The Three Flows

| Flow                | Interface          | Stack                |
| ------------------- | ------------------ | -------------------- |
| Patron purchasing   | Vue 3 web app      | Vue 3 · Pinia · Vite |
| Admin management    | Native desktop app | Tauri · Rust · Vue 3 |
| Door staff scanning | (same desktop app) | Tauri · Rust · Vue 3 |

## Architecture Overview

```txt
theater-app/
├── backend/          # Spring Boot 4 REST API + PostgreSQL
├── frontend/         # Vue 3 patron web app
├── admin/            # Tauri (Rust) native admin desktop app
│   └── src-tauri/    # Rust application shell
├── shared/           # shared Vue + TypeScript library
├── docs/             # Architecture decisions, roadmap, security notes
└── .github/workflows # CI/CD Actions
```

This is a **monorepo** — one repo, four workspaces, one shared component library, one CI pipeline.

## Backend — Spring Boot 4

The API is the core of the system. Key engineering decisions:

### Domain model

```txt
Venue → Stage → Event → Performance → Ticket
```

Seats are **permanent physical records** on a `Stage`, while a seperate table grabs the seats and links them with the performance and tracks performance availability (`AVAILABLE` / `HELD` / `SOLD`). Keeping the physical layout stable and the availability state clean.

### Cart system

- Polymorphic cart items (tickets, merchandise, gift cards) using Java sealed interfaces + Jackson `@JsonTypeInfo` / `@JsonSubTypes`.
- Four-level pricing hierarchy: performance → section → event → global fallback.
- Prices are **snapshot at purchase time** to protect against mid-session changes.
- Seat holds are synchronized within transactions to prevent double-booking.

### Security

- JWT authentication with issuer validation, algorithm allowlisting, and required-claims null checks.
- Rotating refresh token architecture with `__Host-` cookie prefix conventions.
- `Person` as a shared identity anchor; `Patron` and `Staff` as independent associated entities using `@MapsId` (not JPA inheritance).

### API design

- Slug-based event URLs; `display_number` scoped per event for clean performance URLs.
- Spring Data JPA Specifications for reusable, composable query filters.

## Frontend — Vue 3 (Patron App)

### Interactive SVG seating map

- Seats are SVG elements with `data-seat-id` attributes
- Selection/deselection handled via event delegation (`closest()`) — no per-seat listeners
- Pinia store drives seat state; `watch` syncs SVG class state on every change (clear-and-reapply pattern to handle both mutated and replaced arrays)

### Cart store

- Pinia as a reactive in-memory cache hydrated from the server
- Computed subtotal / tax / total pairs keep the UI in sync without redundant API calls

### Patterns

- File-based routing with `watch({ immediate: true })` over `onMounted` for dynamic route params
- Custom fetch wrapper (not Axios) — chosen for native cookie-based auth support
- Global interceptor handles 401 / 403 / 500 uniformly

## Admin App — Tauri + Rust

The admin interface is a **native desktop app** — not an Electron wrapper, not a web app in a frame. Tauri compiles to a lean native binary using the OS webview, with a Rust application shell for system-level operations.

- Shared Vue component library (`@theater-app/shared`) used across both the admin app and the patron web app
- Multi-platform builds (macOS, Windows, Linux) gated to tagged releases in CI

## Infrastructure & Tooling

### CI/CD — GitHub Actions

- Per-workspace jobs: Vue lint / test / build; Rust `fmt` / `clippy` / `test`; Tauri multi-platform builds
- Tauri release builds gated to tagged releases — no accidental deploys

### Monorepo tooling

- ESLint, Prettier, and Vitest configs at repo root, shared across `frontend/`, `shared/`, and `admin/`
- Single source of truth for code style and test configuration

### Observability (planned)

- Grafana LGTM stack (Loki · Grafana · Tempo · Mimir)
- Micrometer + OpenTelemetry for metrics and distributed tracing

## Tech Stack at a Glance

| Layer           | Technology                                            |
| --------------- | ----------------------------------------------------- |
| API             | Spring Boot 4 · Spring Security 7 · Spring Data JPA 4 |
| Database        | PostgreSQL · NeonDB (planned)                         |
| Patron frontend | Vue 3 · Pinia · Vue Router · Vite                     |
| Admin app       | Tauri · Rust · Vue 3                                  |
| Shared UI       | `@theater-app/shared` (Vue component library)         |
| Auth            | JWT · Rotating refresh tokens · `__Host-` cookies     |
| CI/CD           | GitHub Actions                                        |
| Observability   | Grafana LGTM · Micrometer · OpenTelemetry             |
| Data types      | UUIDv7 · BigDecimal · IANA timezones                  |

## What This Project Demonstrates

This is a **solo, part-time build** — designed from a multi-day planning session that produced a roadmap, MVP scope doc, security & scalability notes.

Engineering decisions that reflect deliberate thinking:

- **Polymorphic cart items** using sealed interfaces + Jackson discriminators — extensible without schema changes
- **Dual security filter chains** — patron and staff auth share an identity model but are independently secured
- **Snapshot pricing** — prices locked at cart creation, not recalculated at checkout
- **Seat holds in transactions** — concurrent seat selection doesn't race
- **UUIDv7** — time-ordered UUIDs that are friendly to B-tree indexes
- **`BigDecimal` everywhere money appears** — a deliberate choice, not a default
- **Tauri over Electron** — a meaningful tradeoff toward a smaller binary and native OS integration

These aren't accidental — they're the result of learning the *why* before writing the *what*.

## Roadmap

- [x] Domain model + seating schema
- [x] Auth (JWT + refresh tokens)
- [x] Event / performance / seating APIs
- [x] Cart system (polymorphic items, pricing hierarchy, seat holds)
- [ ] Checkout + payment integration
- [ ] QR code generation + door scanning
- [ ] Admin UI (Tauri app)
- [ ] Observability stack
- [ ] Production deployment
