# API Documentation – Issue Tracking System

## Overview

This project is a Spring Boot–based **Issue Tracking / Post Management System**.

Main features:

- User registration and login
- Role-based access control (`ADMIN`, normal user)
- Create, edit, and submit posts for approval
- Admin review: approve, reject, resolve posts
- View personal posts and approved posts

The backend is built using:

- Spring Boot
- Spring Security
- Spring Data JPA
- BCrypt for password hashing

---

## API Endpoints by Feature

### 1. User registration and login

- `POST /auth/register`
- `POST /auth/login`

---

### 2. Role-based access control (ADMIN, normal user)

- `GET /api/users`
- `POST /api/roles`
- `GET /api/roles`

---

### 3. Create, edit, and submit posts for approval

- `POST /api/posts`
- `PUT /api/posts/{id}`
- `PUT /api/posts/{id}/submit`

---

### 4. Admin review: approve, reject, resolve posts

- `PUT /api/posts/{id}/approve`
- `PUT /api/posts/{id}/reject`
- `PUT /api/posts/{id}/resolve`

---

### 5. View personal posts and approved posts

- `GET /api/posts/my-posts`
- `GET /api/posts/approved`
- `GET /api/posts/all`
- `GET /api/posts/{id}`
