# Theater App Road Map

Roadmap to display the intent with each step along with some notes on how to proceed.

---

## 1.0 - Phase 1 - Product Scope

- **1.1** Clarify MVP Scope

  - 1.1.1 Users: Browse events, pick seats, buy tickets, view tickets ...
  - 1.1.2 Theaters: View events, see orders, manually issue tickets, basic refunds ...
  - 1.1.3 Define user roles: `Customer`, `Staff`, `Admin` — document permissions for each
  - 1.1.4 Define seat states: `AVAILABLE`, `HELD`, `SOLD`, `DISABLED`

- **1.2** Write Core User Stories
  - 1.2.1 User: "As a user I can buy a ticket and receive a QR Code or Bar Code" ...
  - 1.2.2 Theater: "As staff, I can look up a user and issue a ticket to their account" ...
  - 1.2.3 Staff: "As staff, I can scan a ticket at the door and mark it as used"
  - 1.2.4 User: "As a user I can reset my password via email"
  - 1.2.5 User: "As a user I must verify my email before purchasing tickets"

---

## 2.0 - Phase 2 - Backend Foundation

- **2.1** Setup Backend Project (Java, Spring Boot, JPA, Security)

  - 2.1.1 spring-boot-starter-web
  - 2.1.2 spring-boot-starter-security
  - 2.1.3 spring-boot-starter-data-jpa

- **2.2** Setup PostgreSQL

  - 2.2.1 Local PostgreSQL via application.yml
  - 2.2.2 Cloud based NeonDB via application.yml

- **2.3** Design Minimal Schema

  - 2.3.1 Users
  - 2.3.2 Venues — define hierarchy: Venue → Section → Row → Seat
  - 2.3.3 Events — store all datetimes in UTC, convert to local timezone on display
  - 2.3.4 Seats — include `state` column: `AVAILABLE`, `HELD`, `SOLD`, `DISABLED`
  - 2.3.5 Seat Holds — table to track temporary holds (user, seat, expiry timestamp)
  - 2.3.6 Orders
  - 2.3.7 Tickets — include `status` column: `ISSUED`, `SCANNED`, `REFUNDED`
  - 2.3.8 Add DB-level constraints to prevent overselling (unique + check constraints)
  - 2.3.9 ...

- **2.4** Implement Core Entities + Repositories

  - 2.4.1 JPA entities for each table
  - 2.4.2 Repos for basic CRUD

- **2.5** Implement Auth

  - 2.5.1 Email/password auth (JWT or session)
  - 2.5.2 Define and attach roles to JWT claims: `ROLE_CUSTOMER`, `ROLE_STAFF`, `ROLE_ADMIN`
  - 2.5.3 Endpoints:
    - 2.5.3.1 /auth/register
    - 2.5.3.2 /auth/login
    - 2.5.3.3 /auth/me
    - 2.5.3.4 /auth/verify-email (email verification on registration)
    - 2.5.3.5 /auth/forgot-password
    - 2.5.3.6 /auth/reset-password

- **2.6** Implement Core API
  - 2.6.1 /events (list)
  - 2.6.2 /events/{id}/seats (seat map + availability)
  - 2.6.3 /seats/hold (temporarily lock seats for a user during checkout — 10 min TTL)
  - 2.6.4 /seats/release (manually release a hold early)
  - 2.6.5 /orders (create order after payment)
  - 2.6.6 /tickets (list user tickets / get ticket by ID)
  - 2.6.7 /tickets/{id}/scan (mark ticket as SCANNED — staff only)
  - 2.6.8 /refunds (initiate refund for an order or individual ticket)
  - 2.6.9 ...

### 2.7 - Seat Locking & Concurrency

- **2.7.1** Implement seat hold service

  - 2.7.1.1 On /seats/hold: write a hold record with expiry timestamp
  - 2.7.1.2 Prevent other users from holding or purchasing a HELD seat
  - 2.7.1.3 Return hold expiry time to frontend (for countdown timer display)

- **2.7.2** Implement hold expiry job

  - 2.7.2.1 Scheduled job (e.g. every 60s) to release expired holds
  - 2.7.2.2 On expiry: set seat state back to AVAILABLE, delete hold record

- **2.7.3** Concurrency protection
  - 2.7.3.1 Use DB-level locking or optimistic locking on seat state transitions
  - 2.7.3.2 Ensure simultaneous requests for the same seat result in only one hold succeeding

### 2.8 - Backend Testing

- **2.8.1** Unit test services (JUnit, Mockito, Spring Boot Test, TestContainers)

- **2.8.2** Integration tests

  - 2.8.2.1 Auth (register, login, verify email, reset password)
  - 2.8.2.2 Events
  - 2.8.2.3 Seats + hold / release / expiry
  - 2.8.2.4 Orders
  - 2.8.2.5 Ticket scanning
  - 2.8.2.6 Refunds
  - 2.8.2.7 ...

- **2.8.3** Mock external services
  - 2.8.3.1 Stripe
  - 2.8.3.2 Email
  - 2.8.3.3 QR Code / Barcode

### 2.9 - Backend Documentation

- **2.9.1** API Reference (Swagger / OpenAPI)
- **2.9.2** Database Schema
- **2.9.3** Local Dev Setup
- **2.9.4** Environment Variables
- **2.9.5** Testing Instructions
- **2.9.6** Deployment Notes

---

## 3.0 - Phase 3 - Payments

- **3.1** Integrate Stripe Checkout

  - 3.1.1 Create Stripe account + API keys (test + production)
  - 3.1.2 Backend endpoint: /checkout/create-session (amount, event, seats)
  - 3.1.3 Handle webhooks
    - 3.1.3.1 Endpoint: /stripe/webhook
    - 3.1.3.2 On `checkout.session.completed` (successful payment):
      - 3.1.3.2.1 Create order
      - 3.1.3.2.2 Create tickets
      - 3.1.3.2.3 Mark seats as SOLD
      - 3.1.3.2.4 Send order confirmation email with QR code / barcode attached
    - 3.1.3.3 On `checkout.session.expired` (abandoned checkout):
      - 3.1.3.3.1 Release all seat holds associated with the session
      - 3.1.3.3.2 Set seat states back to AVAILABLE

- **3.2** Implement Refunds
  - 3.2.1 Backend endpoint: /refunds (trigger Stripe refund API)
  - 3.2.2 Support full order refunds
  - 3.2.3 Support partial refunds (individual tickets within an order)
  - 3.2.4 On successful refund: update ticket status to REFUNDED, release seat back to AVAILABLE

---

## 4.0 - Phase 4 - Frontend (Vue 3)

- **4.1** Setup Vue (Tailwind, Pinia, Router)

  - 4.1.1 Confirm Tailwind is included — required for responsive mobile layout
  - 4.1.2 Establish mobile-first responsive design as a baseline requirement throughout

- **4.2** Build Core Pages

  - 4.2.1 Event list
  - 4.2.2 Event details + seat selection
    - 4.2.2.1 Show real-time seat availability (AVAILABLE / HELD / SOLD states)
    - 4.2.2.2 Show countdown timer while seats are held during checkout
  - 4.2.3 Login / Register
  - 4.2.4 Email verification prompt (post-registration)
  - 4.2.5 Forgot password / Reset password pages
  - 4.2.6 Checkout
  - 4.2.7 My Tickets (list + QR / barcode)
  - 4.2.8 ...

- **4.3** Connect to Backend via Axios (Auth token / handshake)

- **4.4** Generate QR Codes / Barcodes
  - 4.4.1 QR / barcode payloads must be signed or encrypted — do not expose raw ticket IDs
  - 4.4.2 Validate payload signature on scan (backend)

### 4.5 - Frontend Testing

- **4.5.1** Unit test frontend (Vitest)

- **4.5.2** Test Pinia stores + components

- **4.5.3** Integration tests

  - 4.5.3.1 Login
  - 4.5.3.2 Email verification flow
  - 4.5.3.3 Password reset flow
  - 4.5.3.4 Event browsing
  - 4.5.3.5 Seat selection + hold timer
  - 4.5.3.6 ...

- **4.5.4** End-to-end tests (Cypress)

### 4.6 - Frontend Documentation

- **4.6.1** Component notes
- **4.6.2** Code rules (Prettier)
- **4.6.3** Expected behaviours / how things should act
- **4.6.4** Local dev setup

### 4.7 - Accessibility (a11y)

- **4.7.1** Ensure seat selection UI is keyboard navigable
- **4.7.2** Add ARIA labels to interactive seat map elements
- **4.7.3** Test with a screen reader
- **4.7.4** Ensure sufficient colour contrast for seat state indicators (AVAILABLE / HELD / SOLD)

---

## 5.0 - Phase 5 - Theater Admin Desktop App (Tauri + Vue)

- **5.1** Init Tauri app

- **5.2** Implement auth + API client (same backend auth as web)

  - 5.2.1 Auth must use `ROLE_STAFF` or `ROLE_ADMIN` — reject `ROLE_CUSTOMER` tokens

- **5.3** Build admin views

  - 5.3.1 Login
  - 5.3.2 Events list
  - 5.3.3 Search orders
  - 5.3.4 View orders
  - 5.3.5 Resend ticket
  - 5.3.6 Refund (full + partial)
  - 5.3.7 Ticket scan / invalidation view
    - 5.3.7.1 Scan QR code / barcode at the door
    - 5.3.7.2 Validate ticket signature
    - 5.3.7.3 Display ticket status (ISSUED / SCANNED / REFUNDED)
    - 5.3.7.4 Mark ticket as SCANNED (one-time use enforcement)
  - 5.3.8 ...

- **5.4** Add basic Rust commands in Tauri (config / storage)

---

## 6.0 - Phase 6 - Hardening & Polishing

- **6.1** Validation & error handling

  - 6.1.1 Frontend form validation
  - 6.1.2 Backend request validation + error messages
  - 6.1.3 Security: enforce RBAC on all endpoints using roles defined in 1.1.3
    - 6.1.3.1 `ROLE_CUSTOMER` — browse, purchase, view own tickets
    - 6.1.3.2 `ROLE_STAFF` — scan tickets, view/search orders, issue + refund tickets
    - 6.1.3.3 `ROLE_ADMIN` — all of the above + manage events, venues, users
  - 6.1.4 CORS configuration

- **6.2** Logging + Monitoring
  - 6.2.1 Basic request logging
  - 6.2.2 Simple error boundary in frontend

### 6.3 - CI/CD Pipeline

- **6.3.1** Setup GitHub Actions (or equivalent)
- **6.3.2** Backend pipeline: build → test → Docker image → deploy
- **6.3.3** Frontend pipeline: lint → test → build → deploy
- **6.3.4** Tauri pipeline: build installers for Windows / Mac on tag / release

### 6.4 - Staging Environment

- **6.4.1** Provision a separate staging deployment (same stack as production)
- **6.4.2** Point staging to Stripe test mode API keys
- **6.4.3** Use a separate NeonDB branch or database for staging
- **6.4.4** All deploys go to staging first — promote to production manually

### 6.5 - Secret Management

- **6.5.1** Do not rely on .env files in production
- **6.5.2** Use host platform environment variable management (Render / Fly.io secrets)
- **6.5.3** Document all required environment variables and their purpose
- **6.5.4** Separate secret sets for staging vs production (especially Stripe keys)

### 6.6 - Support & Client Documentation

- **6.6.1** Admin app usage guide
- **6.6.2** Event management instructions
- **6.6.3** Ticket issuing + refund steps
- **6.6.4** Backup / restore instructions (Cloud + Local)
- **6.6.5** Troubleshooting + FAQ

---

## 7.0 - Phase 7 - Deployment

- **7.1** Containerize backend (Docker)

- **7.2** Deploy to staging environment first (see 6.4)

  - 7.2.1 Verify all features against staging before promoting
  - 7.2.2 Confirm Stripe test webhooks are firing correctly in staging

- **7.3** Deploy to production cloud (Render, Fly.io, DO) using NeonDB

- **7.4** Distribute Tauri app
  - 7.4.1 Installers for Windows / Mac
  - 7.4.2 Configure environment (API base URL) via config file or env
  - 7.4.3 Provide separate staging and production config options

### 7.5 - Database Backups / Disaster Recovery

- **7.5.1** Cloud (NeonDB)

  - 7.5.1.1 Scheduled backups
  - 7.5.1.2 Point-in-time recovery
  - 7.5.1.3 Document restore procedures
  - 7.5.1.4 OPTIONAL: Automate snapshot creation via API

- **7.5.2** Local (PostgreSQL)

  - 7.5.2.1 Backup scripts
  - 7.5.2.2 Restore scripts
  - 7.5.2.3 Scheduled tasks
  - 7.5.2.4 "Backup Now" button
  - 7.5.2.5 Document backup / restore procedures

- **7.5.3** Test all backup and restore flows

### 7.6 - Database Documentation

- **7.6.1** Full database documentation
