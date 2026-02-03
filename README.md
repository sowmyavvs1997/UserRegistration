# User Management Portal

A simple full-stack **User Registration and Management** application built with **Vue.js**, **Vite**, **Spring Boot**, and **MongoDB**. It demonstrates basic **CRUD** operations and provides a desktop-like admin UI.

---

## 🚀 Features

- Register users (name, email, mobile, password, DOB)
- View / search / paginate / delete users
- Client-side validation and responsive desktop layout
- REST API (Spring Boot) with MongoDB persistence

---

## 🛠 Tech Stack

- Frontend: **Vue 3**, **Vite**, **Tailwind CSS (CDN for dev)**, **Axios**
- Backend: **Spring Boot**, **Spring Data MongoDB**
- Database (dev): **MongoDB** (Docker Compose provided)

---

## ⚙️ Prerequisites

- Node.js (v18+ recommended) and npm
- Java 17+ and Maven
- Docker & Docker Compose (optional - for MongoDB)

---

## 🧭 Quick start (local)

1. Start MongoDB (recommended via Docker Compose):

   ```bash
   docker-compose up -d
   ```

   This starts a `mongo:7` container and exposes port `27017`.

2. Start the backend API:

   ```bash
   cd backend
   mvn spring-boot:run
   ```

   The backend runs on **http://localhost:8081** (see `backend/src/main/resources/application.properties`).

3. Start the frontend:

   ```bash
   cd frontend
   npm install
   npm run dev
   ```

   The dev server runs on **http://localhost:5173**.

4. Open the app in your browser: http://localhost:5173

---

## 🔌 API (Backend)

Base URL: `http://localhost:8081/api`

Endpoints:

- GET `/users` — list all users
  - Response: 200 OK
  - Body: JSON array of users

- POST `/users` — create a user
  - Request body: JSON with `{ fullName, email, mobile, password, dob }`
  - Response: 201 Created (created user object)

- GET `/users/{id}` — get user by id

- PUT `/users/{id}` — update user
  - Request body: JSON with updated fields

- DELETE `/users/{id}` — delete user

Sample response (array):

```json
[
  {
    "id": "6981ca32ca5fad12406a81ce",
    "fullName": "Ram Kumar",
    "email": "Ramkumar@example.com",
    "mobile": "9876543210",
    "password": "Password@123",
    "dob": "1995-08-15"
  }
]
```

Notes:
- The backend allows CORS from `http://localhost:5173` (see `UserController`), so the frontend can call the API directly during development.
- Passwords in this demo are stored as plain text in the sample; **do not** do this in production — always hash/salt passwords.

---

## 🧩 Frontend notes

- Router is configured with HTML5 history mode (`createWebHistory`). If you refresh a non-root route (e.g. `/users`) you may get a 404 unless the server is configured to return `index.html` for SPA routes. To avoid that in environments where you cannot configure the server, switch to hash mode (`createWebHashHistory`) in `frontend/src/router/index.js`.
- Tailwind is included via CDN in development to avoid build-time PostCSS complexity. For production builds, consider adding Tailwind as a devDependency and configuring PostCSS properly.

---

## 🐛 Troubleshooting

- If Vite shows PostCSS/Tailwind plugin errors, check `postcss.config.cjs` and the presence of required packages (`autoprefixer`, etc.). If you encounter `ETARGET` or package version errors, try cleaning npm cache and installing with `npm install --force`.
- If backend cannot connect to MongoDB, ensure Docker container is running and that the connection URI in `application.properties` points to `mongodb://localhost:27017/userdb`.

---





