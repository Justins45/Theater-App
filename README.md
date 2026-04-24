# Wasda

## File formation

```
theater-app/
│
├── .github/
│   └── workflows/
|       ├── shared.yml         ← lints + builds + tests (if present) on every push
│       ├── backend.yml        ← builds + tests on every push
│       ├── frontend.yml       ← lints + builds on every push 
│       └── tauri.yml          ← builds installers on tagged release
|
├── shared/                    ← shared Vue component library
│   ├── src/
│   │   ├── components/        ← Button, Modal, Badge, SeatMap etc.
│   │   ├── composables/       ← useAuth, useTicket, useSeatStatus etc.
│   │   └── index.js           ← exports everything
│   └── package.json           ← name: "@theater-app/shared"
│
├── backend/                   ← Spring Boot project
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── frontend/                  ← Vue 3 web app (patron flows)
│   ├── src/
│   │   ├── components/        ← Frontend specific components
│   │   ├── pages/             ← full pages (EventList, Checkout etc.)
│   │   ├── stores/            ← Pinia stores
│   │   └── router/            ← Vue Router config
│   ├── package.json
│   └── vite.config.js
│
├── admin/                     ← Tauri + Vue admin app
│   ├── src/                   
│   │   ├── components/        ← Admin specific components
│   │   ├── pages/             ← Vue admin pages
│   │   └── stores/
│   ├── src-tauri/             ← Rust / Tauri config
│   │   ├── src/
│   │   └── tauri.conf.json
│   └── package.json
│
├── docs/                      ← any markdown docs, schema diagrams etc.
│
└── README.md                  ← project overview, how to run each part
```
