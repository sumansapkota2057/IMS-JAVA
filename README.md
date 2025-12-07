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

All responses are wrapped using a common structure (`ApiResponseBuilder`):

```json
{
  "status": "OK",
  "message": "Some message",
  "data": { }
}

