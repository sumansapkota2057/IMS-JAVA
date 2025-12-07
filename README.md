## 🔗 API Endpoints

Base URL (local development):

1. 🔐 Authentication (/auth)
Method	Path	Description	Auth Required	Role
POST	/auth/register	Register a new user	❌ No	Public
POST	/auth/login	Login (validate credentials)	❌ No	Public

Passwords are stored using BCrypt.

Login uses Spring Security’s AuthenticationManager.

2. 👤 User APIs (/api/users)

Controller: UserController
Secured in SecurityConfig with hasRole("ADMIN").

Method	Path	Description	Auth Required	Role
GET	/api/users	Get all system users	✅ Yes	ADMIN only
3. 🏷️ Role APIs (/api/roles)

Controller: RoleController
Also protected with hasRole("ADMIN").

Method	Path	Description	Auth Required	Role
POST	/api/roles	Create a new role	✅ Yes	ADMIN only
GET	/api/roles	Get all roles	✅ Yes	ADMIN only
4. 📝 Post APIs (/api/posts)

Controller: PostController
Secured in SecurityConfig with .requestMatchers("/api/posts","/api/posts/**").authenticated() → any logged-in user.

4.1 Create & edit posts (user)
Method	Path	Description	Auth Required	Condition
POST	/api/posts	Create a new post (saved as DRAFT)	✅ Yes	Any logged-in user
PUT	/api/posts/{id}	Update own post (title/description)	✅ Yes	Must be post owner

Request body for POST /api/posts (PostRequestDTO):

{
  "title": "Lost: Wallet",
  "description": "Black leather wallet lost near library",
  "type": "LOST_ITEM"
}


type is PostType: LOST_ITEM, HELP_REQUEST, ANNOUNCEMENT, COMPLAINT, ISSUE, OTHER.

4.2 Post workflow actions

Business rules are enforced in PostService based on PostStatus (DRAFT, PENDING, APPROVED, REJECTED, RESOLVED).

Method	Path	Description	Auth Required	Condition
PUT	/api/posts/{id}/submit	Submit own DRAFT post → PENDING	✅ Yes	Must be owner, current status = DRAFT
PUT	/api/posts/{id}/approve	Approve a PENDING post → APPROVED	✅ Yes	Intended for admin, status must be PENDING
PUT	/api/posts/{id}/reject	Reject a PENDING post → REJECTED	✅ Yes	Intended for admin, status must be PENDING
PUT	/api/posts/{id}/resolve	Resolve an APPROVED post → RESOLVED	✅ Yes	Intended for admin, status must be APPROVED

Each of these returns a PostResponseDTO with:

id, title, description

type (PostType)

status (PostStatus)

createdAt, updatedAt, approvedAt

user (UserDTO → id, username)

adminNotes

4.3 Fetch posts
Method	Path	Description	Auth Required	Condition
GET	/api/posts/my-posts	Get all posts created by the logged-in user	✅ Yes	Any logged-in user
GET	/api/posts/approved	Get all posts with status APPROVED	✅ Yes	Any logged-in user
GET	/api/posts/all	Get all posts except DRAFT	✅ Yes	Any logged-in user
GET	/api/posts/{id}	Get a single post by ID	✅ Yes	Any logged-in user
5. 🔒 Security Summary

From SecurityConfig:

Public:

POST /auth/register

POST /auth/login

Admin-only:

GET /api/users

GET /api/roles

POST /api/roles

/api/dashboard/** (if present)

Authenticated users:

/api/posts, /api/posts/**

/api/comments/** (if present)

Default:

Any other request → denyAll() (blocked by default)

Passwords are hashed with BCryptPasswordEncoder(10).

User details are loaded via CustomUserDetailsService and wrapped in UserPrincipal.
