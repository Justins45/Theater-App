# Theater App — Security & Scalability Reference

A living reference document covering known security risks and scalability considerations
for the Theater App stack. Review before each major phase and before going live.

---

# Part 1 — Security

---

## S1.0 - Authentication & JWT

- **S1.1** Store JWT in `httpOnly` cookies — never in `localStorage`
  - `localStorage` is readable by any JavaScript on the page (XSS risk)
  - `httpOnly` cookies cannot be read by JavaScript at all
  - This is worth getting right from day one — changing it later touches auth everywhere

- **S1.2** JWT signing secret
  - Must be long, random, and never hardcoded in source code
  - Never commit to `application.yml` or any file tracked by git
  - Store in environment variables only
  - A weak or hardcoded secret allows anyone to mint their own tokens and claim any role

- **S1.3** Token expiry
  - Set a short expiry on access tokens (15–60 minutes)
  - Implement a refresh token strategy for seamless re-authentication
  - Long-lived tokens that get stolen remain valid until expiry — a serious window

- **S1.4** Token invalidation
  - JWTs are stateless — you cannot invalidate them without extra infrastructure
  - If a staff member leaves and their account is deleted, their token still works until expiry
  - Solutions: short expiry + refresh tokens, or a token blocklist in Redis
  - Acceptable for MVP — must be addressed before real staff accounts exist

---

## S2.0 - Role & Access Control (RBAC)

- **S2.1** Enforce roles on every endpoint in the backend — not just the frontend
  - `ROLE_CUSTOMER` — browse, purchase, view own tickets only
  - `ROLE_STAFF` — scan tickets, view/search orders, issue + refund tickets
  - `ROLE_ADMIN` — all of the above + manage events, venues, users
  - Use Spring Security annotations (`@PreAuthorize`) on every controller method

- **S2.2** Tauri admin app is not inherently more trusted than the web app
  - The desktop app still calls the same API over HTTP
  - If a JWT is extracted from the app it can be used directly via curl
  - The backend must enforce roles regardless of which client is calling

- **S2.3** Insecure direct object references (IDOR)
  - A customer hitting `GET /tickets/1234` should only see that ticket if it belongs to them
  - Without an ownership check, any authenticated user can enumerate all tickets by ID
  - Every data fetch endpoint must verify the requesting user owns the resource

---

## S3.0 - Stripe & Payments

- **S3.1** Stripe webhook signature verification — must not be skipped
  - Stripe signs every webhook it sends
  - Without verification, anyone can POST a fake `checkout.session.completed` event
  - A fake event would trigger free ticket creation
  - Stripe provides a library method for this — it takes five minutes to add

- **S3.2** Stripe API keys in source code
  - A Stripe live secret key (`sk_live_...`) committed to GitHub gives full account access
  - Use environment variables exclusively
  - Add `.env` to `.gitignore` before your very first commit — not after

- **S3.3** Test keys in production
  - Deploying with test keys causes real payments to silently fail
  - Separate environment variable sets for staging vs production
  - CI/CD pipeline should make mixing these up impossible

- **S3.4** Idempotency on order creation
  - Processing the same Stripe webhook twice must produce one order, not two
  - Add a unique constraint on `orders.stripe_session_id`
  - Duplicate webhook processing becomes a safe no-op

---

## S4.0 - QR Codes & Ticket Scanning

- **S4.1** Never encode raw ticket IDs in QR payloads
  - A raw UUID in a QR code can be screenshot and reused before scanning
  - Sign QR payloads with HMAC so the backend can verify authenticity
  - The backend validates the signature on every scan attempt

- **S4.2** Scan endpoint must be idempotent
  - `POST /tickets/{id}/scan` must return a clear, consistent response if already `SCANNED`
  - Prevents confusion at the door from network retries or double-taps
  - A ticket already marked `SCANNED` must never appear valid on a second scan

---

## S5.0 - Database & Backend

- **S5.1** SQL injection
  - Spring Data JPA and parameterised queries protect you by default
  - Never build query strings by concatenating user input
  - If writing native queries, always use named parameters

- **S5.2** Mass assignment
  - Never map request bodies directly to JPA entities
  - A malicious user could include `role` or `isAdmin` fields in their request
  - Always use DTOs to explicitly define what fields are accepted from outside

- **S5.3** Database connection string
  - Contains credentials — treat the same as API keys
  - Environment variables only, never committed to source control

- **S5.4** Rate limiting on auth endpoints
  - `/auth/login` without rate limiting can be brute forced
  - Lock an account after N failed attempts, or add request rate limiting
  - Spring has libraries for this — add early, not as an afterthought

---

## S6.0 - Frontend & Tauri

- **S6.1** CORS configuration
  - A wildcard CORS policy (`*`) allows any website to make authenticated requests to your API
  - Lock CORS down to your specific frontend domains from day one
  - Configure in Spring Security, not as an afterthought

- **S6.2** XSS via user generated content
  - Vue escapes template bindings by default — this protects you
  - `v-html` does NOT escape content — never use it with user-supplied data
  - Any user data rendered in the UI must go through Vue's standard template binding

- **S6.3** Tauri credential storage
  - Do not persist auth tokens to plain config files on disk
  - Use Tauri's secure storage API which uses the OS keychain
    - Mac → Keychain
    - Windows → Credential Manager
  - Plain file storage is readable by anyone with access to the machine

---

## S7.0 - Deployment & Infrastructure

- **S7.1** Spring Boot Actuator endpoints
  - Actuator is enabled by default in some configurations and can expose sensitive info
  - Disable or lock down `/actuator` endpoints in production

- **S7.2** Docker image secrets
  - Never `COPY` a `.env` file into a Docker image
  - Never bake secrets into the image at build time
  - Pass secrets at runtime via environment variables on your host platform

- **S7.3** HTTPS enforcement
  - Render and Fly.io provide HTTPS by default
  - Ensure HTTP requests redirect to HTTPS
  - JWTs transmitted over plain HTTP are trivially interceptable

---

## S8.0 - MVP Security Checklist

Address all of these before real users and real money are involved:

| # | Item | Priority |
|---|---|---|
| 1 | JWT stored in `httpOnly` cookie, not `localStorage` | 🔴 Must Have |
| 2 | Stripe webhook signature verification | 🔴 Must Have |
| 3 | Stripe + DB secrets in environment variables only | 🔴 Must Have |
| 4 | CORS locked to your domain, not wildcard | 🔴 Must Have |
| 5 | Ownership checks on every data endpoint | 🔴 Must Have |
| 6 | Role enforcement on every admin/staff endpoint | 🔴 Must Have |
| 7 | QR payload signing (HMAC) | 🟡 Before First Event |
| 8 | Rate limiting on `/auth/login` | 🟡 Before First Event |
| 9 | Token expiry + refresh token strategy | 🟡 Before First Event |
| 10 | Actuator endpoints disabled in production | 🟢 Before Public Launch |
| 11 | HTTPS enforced, no plain HTTP | 🟢 Before Public Launch |
| 12 | Tauri using OS keychain for token storage | 🟢 Before Public Launch |

---
---

# Part 2 — Scalability

---

## SC1.0 - Understanding the Scale Target

30,000 tickets/day is approximately **20 purchases per minute on average** — but ticket
sales are never evenly distributed. A popular show going on sale at 10am can produce
**500–1,000 concurrent users in the first few minutes.**

That spike is what breaks systems, not the daily average. Design for the spike.

---

## SC2.0 - Database (Highest Risk)

- **SC2.1** Connection pooling — enable from day one
  - HikariCP is built into Spring Boot — configure it explicitly, don't rely on defaults
  - NeonDB has PgBouncer built in — enable it
  - This multiplexes many app connections into fewer DB connections
  - Single biggest scalability win with zero application code changes

- **SC2.2** Index critical queries at table creation time
  - Add indexes when you create the tables, not when things get slow

  ```sql
  -- Seats table — most queried by event and state
  CREATE INDEX idx_seats_event_state ON seats(event_id, state);

  -- Tickets table — queried by user frequently
  CREATE INDEX idx_tickets_order ON tickets(order_id);

  -- Orders table — queried by user frequently
  CREATE INDEX idx_orders_user ON orders(user_id);
  ```

- **SC2.3** Unique constraint on `orders.stripe_session_id`
  - Prevents duplicate orders from webhook retries
  - Is both a scalability and security measure

  ```sql
  ALTER TABLE orders ADD CONSTRAINT uq_stripe_session UNIQUE (stripe_session_id);
  ```

- **SC2.4** Avoid N+1 queries
  - Loading a list of orders and then querying tickets for each one individually will kill
    performance at scale
  - Use JPA `JOIN FETCH` or `@EntityGraph` to load related data in a single query
  - Check your queries early — they're invisible problems until traffic hits

---

## SC3.0 - Seat Availability (Hot Read Problem)

Every user browsing a popular event reads the same seat rows simultaneously.
At 1,000 concurrent users that is 1,000 reads per second on the same data.

- **SC3.1** Design the seat availability endpoint to be cache-friendly from day one
  - Return seat state as simple, flat data
  - Avoid computed fields or complex joins in the availability response
  - Keep the response shape stable — this makes adding a cache later trivial

- **SC3.2** Future upgrade path: Redis cache for seat availability
  - When a seat is held or sold, invalidate the cache for that event
  - Moves the hot read load off PostgreSQL entirely
  - Adding Redis later is straightforward if the endpoint is already clean

---

## SC4.0 - Stripe Webhook Processing

At scale, hundreds of webhook events fire per minute. Synchronous processing
(receive → process → respond) backs up under load and causes Stripe to retry,
which can result in duplicate order processing.

- **SC4.1** Acknowledge webhooks immediately, process asynchronously
  - Return HTTP 200 to Stripe before doing any processing work
  - In Spring Boot: return the response, then process in a separate thread or async method
  - This is a small design decision that prevents a large problem

- **SC4.2** Future upgrade path: message queue between webhook and order processor
  - Webhook drops a message on the queue and returns 200 instantly
  - Workers process the queue independently at their own pace
  - Completely decouples payment flow from traffic spikes
  - Options: RabbitMQ, AWS SQS, or Redis Streams

---

## SC5.0 - Backend Horizontal Scaling

Spring Boot is stateless by design — you can run multiple instances behind a
load balancer and scale horizontally by adding instances. Keep it that way.

- **SC5.1** Never store state in memory between requests
  - No in-memory session state (JWT handles this — you're already doing this)
  - No in-memory caches that would differ between instances
  - No local file writes — use cloud storage if files are ever needed

- **SC5.2** Future upgrade path: multiple instances + load balancer
  - Render and Fly.io both support this without any application code changes
  - Just increase the instance count in your hosting config

---

## SC6.0 - Frontend (Already Solved)

Vue compiled to static files is essentially infinitely scalable.

- Vercel and Netlify serve assets from a global CDN
- Canadian and US users both get fast load times from edge nodes near them
- No changes needed here at any scale

---

## SC7.0 - Codebase Decisions That Make Scaling Easier

Small decisions made now that make future scaling surgical rather than invasive:

- **SC7.1** Use a service layer properly
  - Keep business logic in service classes, not controllers or repositories
  - When you add a message queue later, only what *calls* the service changes — not the service itself

- **SC7.2** Use DTOs on all API endpoints
  - Never return JPA entities directly from controllers
  - When you change your schema, your API contract stays stable
  - Prevents accidental data exposure (entity fields you didn't mean to return)

- **SC7.3** Environment-based configuration everywhere
  - Every external service URL, secret, and connection string in environment variables
  - Swapping NeonDB for RDS or adding Redis = change one env var, nothing in the codebase

- **SC7.4** Idempotency on all critical write operations
  - Order creation, ticket creation, seat state changes must be safe to run twice
  - Unique constraints at the DB level are your safety net

---

## SC8.0 - Architecture Upgrade Path

The system evolves additively — nothing gets ripped out, layers get added on top.

```
MVP — Launch
──────────────────────────────────────────
Vue (Vercel CDN)
    → Spring Boot (single instance)
        → PgBouncer
            → PostgreSQL (NeonDB)
    ↕
Stripe Webhooks (synchronous processing)


~5,000 users/day — Tune existing setup
──────────────────────────────────────────
+ Enable HikariCP connection pool tuning
+ Fix any N+1 queries found in logs
+ Verify all indexes are in place


~10,000–30,000 users/day — Add caching + async
──────────────────────────────────────────
Vue (Vercel CDN)
    → Load Balancer
        → Spring Boot (x2–3 instances)
            → Redis (seat availability cache)
            → PgBouncer
                → PostgreSQL (NeonDB + read replica)
    ↕
Stripe Webhooks
    → Message Queue
        → Order Processor Workers


Beyond 30,000/day — You will know what you need by then
```

---

## SC9.0 - Scalability Readiness Checklist

| # | Item | When |
|---|---|---|
| 1 | HikariCP connection pool configured | MVP |
| 2 | NeonDB PgBouncer enabled | MVP |
| 3 | Database indexes on seats, tickets, orders | MVP |
| 4 | Unique constraint on `orders.stripe_session_id` | MVP |
| 5 | No N+1 queries in seat availability endpoint | MVP |
| 6 | Webhook returns 200 before processing | MVP |
| 7 | Service layer separates business logic from controllers | MVP |
| 8 | DTOs on all API responses | MVP |
| 9 | No in-memory state between requests | MVP |
| 10 | Redis cache for seat availability | ~5,000 users/day |
| 11 | Second backend instance + load balancer | ~10,000 users/day |
| 12 | Async webhook processing via message queue | ~10,000 users/day |
| 13 | PostgreSQL read replica | ~20,000 users/day |