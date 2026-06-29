# Merge - Engineering Formation Platform

Merge is an Engineering Formation Platform designed for Nigerian CS undergraduates. Rather than acting as a standard boot camp, LMS, or course marketplace, it transforms first-year university students into trusted software engineers through deliberate, hands-on engineering formation over 3.5 years.

This repository hosts the modular monolith project skeleton for both the backend and frontend.

## 🏗️ Project Architecture & Structure

The codebase is organized into two primary segments matching the local development and containerized production stacks:

```
MERGE/
├── PRD/                             # PDF Requirements Documents
├── backend/                         # Java 21 & Spring Boot 3.3.0 Monolith
│   ├── Dockerfile
│   ├── pom.xml                      # Maven Dependencies (Web, JPA, Security, Redis, Testcontainers)
│   └── src/
│       ├── main/
│       │   ├── java/com/merge/backend/
│       │   │   ├── MergeBackendApplication.java
│       │   │   ├── identity/        # Identity Module (Auth, profiles, token encryption)
│       │   │   ├── curriculum/      # Curriculum Module (Concepts, resource delivery)
│       │   │   ├── assessment/      # Assessment Module (Drills, Judge0, Builds, checks)
│       │   │   ├── progression/     # Progression Module (XP awards, transaction isolation, capping)
│       │   │   ├── engagement/      # Engagement Module (Sessions, weekly momentum, seasons)
│       │   │   ├── feedback/        # Feedback Module (Clean code feedback, peer reviews)
│       │   │   ├── competency/      # Competency Module (SFIA metrics, engineering identity)
│       │   │   ├── ai/              # AI Module (Gemini gateway & prompt orchestration)
│       │   │   └── integration/     # Integration Module (GitHub, Judge0, Cloudflare R2 services)
│       │   └── resources/
│       │       └── application.properties
│       └── test/
├── frontend/                        # React 18 & Vite PWA (Progressive Web App)
│   ├── Dockerfile
│   ├── nginx.conf
│   ├── index.html
│   ├── package.json
│   ├── vite.config.js               # Service Worker & PWA Caching Rules
│   └── src/
│       ├── App.jsx                  # Initial Dashboard Skeleton
│       ├── index.css                # Premium Dark Theme Styles (Outfit font)
│       └── main.jsx
├── docker-compose.yml               # Container Orchestration (App, DB, Redis, Judge0)
├── .env.example                     # Environment Variables Template
└── .gitignore                       # Global Exclusions
```

---

## 🛠️ Technology Stack

### Backend
- **Language**: Java 21 (LTS)
- **Framework**: Spring Boot 3.x
- **Build Tool**: Maven
- **Database**: Supabase PostgreSQL 15 + `pgvector` extension
- **Cache/Queue**: Redis 7
- **Sandbox Execution**: Judge0 (Self-hosted for isolated execution of student submissions)
- **AI Integration**: Google Gemini API

### Frontend
- **Framework**: React 18 (Vite-powered SPA)
- **Type**: Progressive Web App (PWA) with offline capabilities
- **Styling**: Premium Dark Theme with Outfit typography & micro-interactions

---

## 🚀 Getting Started

### Prerequisites
- Docker & Docker Compose
- Java 21 JDK & Maven (for local backend compilation outside Docker)
- Node.js 20+ & npm (for local frontend development outside Docker)

### Setup & Run
1. Copy the environment variables:
   ```bash
   cp .env.example .env
   ```
2. Open `.env` and fill in your details (especially your `GEMINI_API_KEY`).
3. Start the entire stack with Docker Compose:
   ```bash
   docker-compose up --build
   ```
4. Access the applications:
   - **Frontend**: http://localhost:3000
   - **Backend API**: http://localhost:8080

---

## 🛡️ Critical Engineering Rules (PRD Compliance)

Please ensure all commits to this repository strictly adhere to the following rules defined in the system requirements:
1. **Token Security**: Gemini tokens must **never** touch the frontend. They are stored encrypted in Supabase and only decrypted inside `TokenEncryptionService` on the backend.
2. **XP Integrity**: XP calculations and awards must run entirely server-side using atomic database transactions (`SELECT FOR UPDATE`).
3. **Comprehension Timer**: The 10-second timer for student comprehension checks is enforced server-side.
4. **Idempotency**: All drill and build submissions must contain a unique `idempotencyKey` and check it before processing to prevent double-submitting.
5. **Code Execution Sandbox**: Judge0 runs with `ENABLE_NETWORK=false`, a CPU limit of 2 seconds, and 256MB memory.
6. **PWA Caching**: Do not cache API endpoints, submissions, or authentication tokens in the service worker.
