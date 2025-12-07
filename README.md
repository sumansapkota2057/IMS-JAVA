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
Authentication & Authorization
Authentication

Uses Spring Security with HTTP Basic authentication.

Credentials: username + password

Passwords are stored using BCryptPasswordEncoder(10).

Authorization (Roles)

Defined in SecurityConfig:

Public (no authentication required):

POST /auth/register

POST /auth/login

Admin-only:

GET /api/users

GET /api/roles

POST /api/roles

/api/dashboard/** (if present)

Any authenticated user:

/api/posts, /api/posts/**

/api/comments/** (if present)

Any other request:

Denied (denyAll())

1. Authentication APIs (/auth)
1.1 Register User

Endpoint

POST /auth/register


Description

Register a new user.

Authentication

Not required (public).

Request Body

Represents the User entity fields needed for registration:

{
  "username": "johndoe",
  "email": "johndoe@example.com",
  "fullName": "John Doe",
  "password": "password123",
  "roles": [
    { "roleName": "USER" }
  ]
}


Response Example

{
  "status": "CREATED",
  "message": "User registered successfully",
  "data": null
}

1.2 Login

Endpoint

POST /auth/login


Description

Validate user credentials.

Authentication

Not required for the call itself.

After login, Spring Security authenticates the user using AuthenticationManager.

Request Body

{
  "username": "johndoe",
  "password": "password123"
}


Response Example

{
  "status": "OK",
  "message": "User logged in successfully",
  "data": null
}

2. User APIs (/api/users)

Controller: UserController
Secured with hasRole("ADMIN").

2.1 Get All Users

Endpoint

GET /api/users


Description

Retrieve all registered users.

Authentication / Authorization

Requires authentication.

Only users with role ADMIN are allowed.

Response Example

{
  "status": "OK",
  "message": "Users fetched successfully",
  "data": [
    {
      "id": 1,
      "username": "admin",
      "email": "admin@example.com",
      "fullName": "Admin User",
      "active": true,
      "roles": [
        {
          "id": 1,
          "roleName": "ADMIN"
        }
      ]
    }
  ]
}

3. Role APIs (/api/roles)

Controller: RoleController
Secured with hasRole("ADMIN").

3.1 Create Role

Endpoint

POST /api/roles


Description

Create a new role.

Authentication / Authorization

Requires authentication.

Only ADMIN users are allowed.

Request Body

{
  "roleName": "ADMIN"
}


Response Example

{
  "status": "CREATED",
  "message": "Role added successfully",
  "data": null
}

3.2 Get All Roles

Endpoint

GET /api/roles


Description

Retrieve all roles.

Authentication / Authorization

Requires authentication.

Only ADMIN users are allowed.

Response Example

{
  "status": "OK",
  "message": "Roles fetched successfully",
  "data": [
    {
      "id": 1,
      "roleName": "ADMIN"
    },
    {
      "id": 2,
      "roleName": "USER"
    }
  ]
}

4. Post APIs (/api/posts)

Controller: PostController
All /api/posts endpoints require authentication.

Main model: Post
Important fields:

title: String

description: String

postType: PostType (LOST_ITEM, HELP_REQUEST, ANNOUNCEMENT, COMPLAINT, ISSUE, OTHER)

postStatus: PostStatus (DRAFT, PENDING, APPROVED, REJECTED, RESOLVED)

user: User

createdAt, updatedAt, approvedAt

adminNotes

4.1 Create Post

Endpoint

POST /api/posts


Description

Create a new post. New posts are created with status DRAFT.

Authentication / Authorization

Requires authentication.

Any logged-in user can create posts.

Request Body (PostRequestDTO)

{
  "title": "Lost: Wallet",
  "description": "Black leather wallet lost near the library.",
  "type": "LOST_ITEM"
}


Response Example (PostResponseDTO)

{
  "status": "CREATED",
  "message": "Post created successfully",
  "data": {
    "id": 1,
    "title": "Lost: Wallet",
    "description": "Black leather wallet lost near the library.",
    "type": "LOST_ITEM",
    "status": "DRAFT",
    "createdAt": "2025-12-07T12:00:00",
    "updatedAt": "2025-12-07T12:00:00",
    "approvedAt": null,
    "adminNotes": null,
    "user": {
      "id": 2,
      "username": "johndoe"
    }
  }
}

4.2 Submit Post for Approval

Endpoint

PUT /api/posts/{id}/submit


Description

Submit a DRAFT post for admin approval. Status changes from DRAFT → PENDING.

Authentication / Authorization

Requires authentication.

Only the owner of the post can submit it.

Only posts with status DRAFT can be submitted.

Path Variable

id – Post ID.

4.3 Approve Post (Admin)

Endpoint

PUT /api/posts/{id}/approve


Description

Approve a PENDING post. Status changes from PENDING → APPROVED.

Authentication / Authorization

Requires authentication.

Intended for ADMIN users (should be restricted in security config).

Only posts with status PENDING can be approved.

Path Variable

id – Post ID.

Query Parameter (optional)

notes – Admin notes for approval.

4.4 Reject Post (Admin)

Endpoint

PUT /api/posts/{id}/reject


Description

Reject a PENDING post. Status changes from PENDING → REJECTED.

Authentication / Authorization

Requires authentication.

Intended for ADMIN users.

Only posts with status PENDING can be rejected.

Path Variable

id – Post ID.

Query Parameter (optional)

notes – Reason for rejection.

4.5 Resolve Post (Admin)

Endpoint

PUT /api/posts/{id}/resolve


Description

Mark an APPROVED post as RESOLVED.

Authentication / Authorization

Requires authentication.

Intended for ADMIN users.

Only posts with status APPROVED can be resolved.

Path Variable

id – Post ID.

Query Parameter (optional)

notes – Resolution notes.

4.6 Update Post (Owner)

Endpoint

PUT /api/posts/{id}


Description

Update title and/or description of a post.

Authentication / Authorization

Requires authentication.

Only the owner of the post can update it.

Path Variable

id – Post ID.

Request Body (PostUpdateDTO)

{
  "title": "Updated title",
  "description": "Updated description",
  "status": null,
  "adminNotes": null
}


Only non-null title/description are updated.

4.7 Get My Posts

Endpoint

GET /api/posts/my-posts


Description

Get all posts created by the currently authenticated user.

Authentication / Authorization

Requires authentication.

Response Example

{
  "status": "OK",
  "message": "Posts retrieved successfully",
  "data": [
    {
      "id": 1,
      "title": "Lost: Wallet",
      "description": "Black leather wallet lost near the library.",
      "type": "LOST_ITEM",
      "status": "DRAFT",
      "createdAt": "2025-12-07T12:00:00",
      "updatedAt": "2025-12-07T12:00:00",
      "approvedAt": null,
      "adminNotes": null,
      "user": {
        "id": 2,
        "username": "johndoe"
      }
    }
  ]
}

4.8 Get Approved Posts

Endpoint

GET /api/posts/approved


Description

Get all posts with status APPROVED, ordered by createdAt descending.

Authentication / Authorization

Requires authentication (according to SecurityConfig).

Any logged-in user.

4.9 Get All Non-Draft Posts

Endpoint

GET /api/posts/all


Description

Get all posts where status is not DRAFT (PENDING, APPROVED, REJECTED, RESOLVED).

Authentication / Authorization

Requires authentication.

4.10 Get Post by ID

Endpoint

GET /api/posts/{id}


Description

Get a single post by its ID.

Authentication / Authorization

Requires authentication.

Path Variable

id – Post ID.

5. Data Models (Summary)
5.1 User

From User entity:

id: Long

username: String (unique)

email: String (unique)

fullName: String

password: String (BCrypt hashed)

active: boolean

roles: List<Role>

5.2 Role

From Role entity:

id: Long

roleName: String (e.g. ADMIN, USER)

5.3 Post

From Post entity:

id: Long

title: String

description: String

postType: PostType

postStatus: PostStatus

user: User

createdAt: LocalDateTime

updatedAt: LocalDateTime

approvedAt: LocalDateTime

adminNotes: String

5.4 Enums

PostType

LOST_ITEM

HELP_REQUEST

ANNOUNCEMENT

COMPLAINT

ISSUE

OTHER

PostStatus

DRAFT

PENDING

APPROVED

REJECTED

RESOLVED

6. Typical Workflow

User registers via POST /auth/register.

User logs in via POST /auth/login.

User creates a post (POST /api/posts) → status = DRAFT.

User edits the post if needed (PUT /api/posts/{id}).

User submits post for approval (PUT /api/posts/{id}/submit) → status = PENDING.

Admin reviews PENDING posts and:

Approves (PUT /api/posts/{id}/approve) → APPROVED, or

Rejects (PUT /api/posts/{id}/reject) → REJECTED.

Admin resolves an approved post (PUT /api/posts/{id}/resolve) → RESOLVED.

Users can view:

Their own posts: GET /api/posts/my-posts

Approved posts: GET /api/posts/approved

