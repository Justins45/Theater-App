# Theater App

A full-stack theater ticketing platform. Patrons browse events, pick seats, and purchase tickets through the web app. Theater staff manage orders and scan tickets at the door through the desktop admin app. A shared REST API powers both surfaces.

_**NOTE: not all portions of this README.md are fully setup, some parts are still in development and will be added soon.**_

_**NOTE: This program was written and testing on Mac OS Tahoe v26.3. Not all Operating systems are fully tested as of may 4th 2026.**_

## What's in This Repo

```txt
theater-app/
├── .github/
│   └── workflows/       ← CI/CD pipelines
├── backend/             ← Spring Boot REST API
├── frontend/            ← Vue 3 web app (patron-facing)
├── admin/               ← Tauri + Vue desktop admin app
└── docs/
```

## Running Locally

Start each part of the project in its own terminal (_ran from root directory_):

**Make sure you have your databse information inside the** `backend/src/main/resources/secretes.properties` **file .**

| Service      | Command                                                 |
| ------------ | ------------------------------------------------------- |
| Dependencies | `npm install`                                           |
| Frontend     | `npm run start:frontend`                                |
| Admin        | `npm run start:admin`                                   |
| Backend      | `npm run start:backend`                                 |
| Database     | Start PostgreSQL via your local service manager         |

The backend runs on `localhost:8080` and the frontend on `localhost:5173` and admin runs on `localhost:1420` by default.

## Main Sections

### Backend (`/backend`)

---

A Spring Boot REST API that serves both the web app and the desktop admin app. It handles all business logic, authentication, seat locking, payment processing, and ticket management.

The backend serves to gather information for the front end, and take in and return information for the admin sections. 

### Frontend (`/frontend`)

---

A Vue 3 web app for patrons. Users browse events, select seats on a seat map, check out via Stripe, and view their tickets with QR codes.

The frontend communicates exclusively with the backend API. `VITE_API_BASE_URL` is the single configuration point for the API address across environments.

### Admin (`/admin`)

---

A Tauri desktop app (Windows and Mac) for theater staff and administrators. Built with Vue 3 using the same patterns as the frontend where possible.

Intended to be a standalone app for easy access and easy seperation of content flow for admins and staff using the program.

## Tech Stack

| Layer        | Technology                                          |
| ------------ | --------------------------------------------------- |
| Backend      | Java, Spring Boot, Spring Security, Spring Data JPA |
| Database     | PostgreSQL (local), NeonDB (staging/production)     |
| Web frontend | Vue 3, Pinia, Vue Router, Axios, SASS, Vite         |
| Desktop      | Tauri (Rust shell), Vue 3, SASS                     |
| Payments     | Stripe Checkout + Webhooks                          |
| Tickets      | QR codes (HMAC-signed payloads)                     |
| CI/CD        | GitHub Actions                                      |
| Containers   | Docker                                              |

## General Setup

### Prerequisites

Before running any part of the project you will need the following installed:

- **Java 21+** — for the Spring Boot backend
- **Node.js 20+** — for the Vue frontends and Vite build tooling
- **Rust + Cargo** — for Tauri (the desktop app shell)
- **PostgreSQL** — local database for development
- **Docker** — optional, for running the backend in a container

You will also need accounts and credentials for:

- **Stripe** — test API keys for local development  
- **NeonDB** — only required for testing on the cloud

### Configuration and Secrets

All secrets are passed via environment variables. Nothing is hardcoded or committed to git.

Each part of the project has its own environment configuration:

- **Backend** Required variables include the database connection string, JWT secret, and Stripe keys. See `/backend` for the full list.
- **Frontend and Admin** use Vite environment files (`.env`). `VITE_API_BASE_URL` is the only required variable pointing to the backend API address. See `/frontend` and `/admin` respectively.

Docker images never have secrets baked in — they are always passed at runtime.

### Installation

Each part of the project is set up independently. Refer to the README in each directory for exact commands:

- **`/backend`** — Maven build, database migrations, and how to run the Spring Boot server locally
- **`/frontend`** — npm install and Vite dev server
- **`/admin`** — npm install and Tauri dev mode (requires Rust toolchain)

### CI/CD

Three GitHub Actions pipelines run automatically:

- **`backend.yml`** — triggers on pushes to `backend/**`; builds the jar and runs tests
- **`frontend.yml`** — triggers on pushes to `frontend/**`; lints and builds the Vue app
- **`admin.yml`** — triggers on pushes to `admin/**`; lints and runs tests || also triggers on tagged releases (e.g. `v1.0.0`); builds and attaches Windows and Mac installers to the GitHub Release
