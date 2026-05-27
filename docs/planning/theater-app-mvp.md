# Theater App — MVP Roadmap

## Patron Purchase · Admin Management · Door Scanning

This is the minimum path to a working product covering three core flows:

- **Patron** — Browse events → Select seat → Pay → Receive QR code
- **Admin** — View orders → Look up a ticket → Manually issue a ticket
- **Door Staff** — Scan QR code → See ticket status → Mark as scanned

---

## 1.0 - Foundation

- **1.1** Define the three roles: `ROLE_CUSTOMER`, `ROLE_STAFF`, `ROLE_ADMIN` ✅
- **1.2** Define seat states: `AVAILABLE`, `HELD`, `SOLD`
- **1.3** Define ticket statuses: `ISSUED`, `SCANNED`

---

## 2.0 - Project Setup & CI/CD

> Set this up before writing a single line of application code.
> It will serve you for the entire project.

- **2.1** Create GitHub repository ✅
  - 2.1.1 Consider making it public — eliminates the Actions minute quota entirely and serves as a portfolio piece ✅

- **2.2** Backend CI pipeline (GitHub Actions)
  - 2.2.1 Trigger on every push to main ✅
  - 2.2.2 Build Spring Boot jar ✅
  - 2.2.3 Run tests (scaffold this now even if no tests exist yet — adding them later becomes trivial) ✅

- **2.3** Frontend CI pipeline (GitHub Actions)
  - 2.3.1 Trigger on every push to main ✅
  - 2.3.2 Lint + build Vue app ✅

- **2.4** Tauri CD pipeline (GitHub Actions)
  - 2.4.1 Trigger on tagged releases only (e.g. `v1.0.0`) — Tauri builds are slow, don't run on every push
  - 2.4.2 Build Windows + Mac installers
  - 2.4.3 Attach installers to GitHub Release automatically

- **2.5** Setup databases
  - 2.5.1 Local PostgreSQL ✅ via application.yml
  - 2.5.2 NeonDB (cloud) via application.yml

- **2.6** Setup deployment targets
  - 2.6.1 Backend: Render or Fly.io (free tier, connect to NeonDB)
  - 2.6.2 Frontend: Vercel or Netlify (free tier, auto-deploys on push to main)

> At this point you have a skeleton that auto-builds and auto-deploys on every push.
> Everything from here is just filling it in.

---

## 3.0 - Backend

- **3.1** Setup Spring Boot project (web ✅, security, data-jpa ✅)

- **3.2** Minimal schema — only what the three flows touch
  - 3.2.1 Users (id, email, password, role) ✅
  - 3.2.2 Venues (id, name, timezone) ✅ (hard written in db)
  - 3.2.3 Events (id, venue_id, name, datetime Local)
  - 3.2.4 Seats (id, event_id, label, state)
  - 3.2.5 Seat Holds (id, user_id, seat_id, expires_at)
  - 3.2.6 Orders (id, user_id, created_at)
  - 3.2.7 Tickets (id, order_id, seat_id, qr_payload, status, venu timezone)

- **3.3** Auth
  - 3.3.1 /auth/register ✅
  - 3.3.2 /auth/login (returns JWT with role claim attached) ✅
  - 3.3.3 /auth/me

- **3.4** Core API — only what the three flows need
  - 3.4.1 GET /events — list events
  - 3.4.2 GET /events/{id}/seats — seat map with availability
  - 3.4.3 POST /seats/hold — hold seats during checkout (10 min TTL)
  - 3.4.4 POST /checkout/create-session — create Stripe session
  - 3.4.5 POST /stripe/webhook — on payment complete: create order + tickets + mark seats SOLD
  - 3.4.6 GET /tickets — list tickets for logged in user
  - 3.4.7 GET /tickets/{id} — get single ticket + QR payload
  - 3.4.8 POST /tickets/{id}/scan — mark ticket as SCANNED (staff only)
  - 3.4.9 POST /admin/tickets/issue — manually issue a ticket to a user (admin only)
  - 3.4.10 GET /admin/orders — list all orders (admin only)

- **3.5** Seat hold expiry
  - 3.5.1 Scheduled job to release expired holds back to AVAILABLE
  - 3.5.2 Stripe webhook: on `checkout.session.expired` → release associated holds

- **3.6** API Docs
  - 3.6.1 Setup Swagger/Springdoc OpenAP to view API end-points ✅
  - 3.6.2 Setup and Use Postman to test all API end-points to make sure they do the correct things ✅

---

## 4.0 - Payments (Stripe)

> Stripe test mode is completely free. No charges until you go live.

- **4.1** Create Stripe account + generate test mode API keys
- **4.2** Implement /checkout/create-session endpoint
- **4.3** Handle webhooks
  - 4.3.1 `checkout.session.completed` → create order, create tickets, mark seats SOLD
  - 4.3.2 `checkout.session.expired` → release all seat holds for the session

---

## 5.0 - Web Frontend — Patron Flows (Vue 3)

- **5.1** Setup Vue + SCSS + Pinia + Router ✅
- **5.2** Event list page
- **5.3** Event detail + seat selection page
  - 5.3.1 Show real-time seat availability (AVAILABLE / HELD / SOLD)
  - 5.3.2 Show countdown timer while seats are held
- **5.4** Login / Register page
- **5.5** Checkout page (Stripe redirect)
- **5.6** My Tickets page — list tickets + display QR code per ticket

---

## 6.0 - Admin Views (Vue first, Tauri second)

> Build these as standard Vue pages first and get them fully working in the browser
> against the real API. Then wrap in Tauri. This separates two learning curves —
> don't try to learn Tauri and build features at the same time.

- **6.1** Admin: Orders list page
- **6.2** Admin: Order detail page (view tickets on an order)
- **6.3** Admin: Manually issue a ticket to a user
- **6.4** Staff: Ticket scan page
  - 6.4.1 Input QR code value (typed or camera scan)
  - 6.4.2 Display ticket details + current status
  - 6.4.3 Button to mark as SCANNED

---

## 7.0 - Tauri Wrapper

> By this point all admin Vue views are done and tested in the browser.
> Tauri is now just a shell around work you've already completed.

- **7.1** Init Tauri project, point it at existing Vue admin views
- **7.2** Configure API base URL via Tauri config (staging vs production)
- **7.3** Auth integration — same JWT flow as web, no backend changes needed
- **7.4** Basic Rust commands for local config storage
  - 7.4.1 Remember API base URL
  - 7.4.2 Persist auth token between sessions
- **7.5** Test all three admin flows inside the Tauri shell
  - 7.5.1 View and search orders
  - 7.5.2 Manually issue a ticket
  - 7.5.3 Scan a ticket and mark as scanned
- **7.6** Tag a release → CI/CD pipeline builds and attaches installers automatically

---

## 8.0 - Ship It

- **8.1** Switch Stripe from test mode to production keys
- **8.2** Smoke test all three flows end-to-end in production
  - 8.2.1 Patron browses events, buys a ticket, receives QR code
  - 8.2.2 Admin views the order and manually issues a ticket
  - 8.2.3 Staff scans the ticket and marks it as scanned
- **8.3** Confirm Stripe webhooks are firing correctly in production

---

## Realistic Timeline (Evenings + Weekends)

| Phase                     | Estimate        |
| ------------------------- | --------------- |
| 1.0 Foundation            | 1–2 days        |
| 2.0 CI/CD + project setup | 1–2 weekends    |
| 3.0 Backend               | 4–6 weeks       |
| 4.0 Stripe                | 1–2 weekends    |
| 5.0 Vue patron flows      | 3–4 weeks       |
| 6.0 Admin Vue views       | 2–3 weeks       |
| 7.0 Tauri wrapper         | 1–2 weekends    |
| 8.0 Deploy + smoke test   | 1 weekend       |
| **Total**                 | **~4–5 months** |

---

## Deliberately Left Out of MVP

These are all real features that live in the full roadmap.
They are deferred — not abandoned.

| Feature                             | Where It Lives             |
| ----------------------------------- | -------------------------- |
| Password reset / email verification | Full roadmap 2.5, 4.2      |
| Refunds                             | Full roadmap 3.2           |
| QR payload signing / encryption     | Full roadmap 4.4           |
| Staging environment                 | Full roadmap 6.4           |
| Unit + integration tests            | Full roadmap 2.8, 4.5      |
| All documentation phases            | Full roadmap 2.9, 4.6, 6.6 |
| Logging + monitoring                | Full roadmap 6.2           |
| Backup / disaster recovery          | Full roadmap 7.5           |
| Accessibility (a11y)                | Full roadmap 4.7           |
| OAuth / social login                | Full roadmap (future)      |
