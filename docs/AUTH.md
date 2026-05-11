# Authentication Flow

The Mini Banking Application uses JWT (JSON Web Token)–based authentication to secure its REST APIs.
Authentication is stateless: the server does not store session information. Each request to a protected endpoint must include a valid JWT.

# Authentication Overview

A user logs in using username and password.
The server verifies the credentials.
A JWT is generated and returned to the client.
The client includes the token in subsequent requests.
The server validates the token before processing the request.

# Login Endpoint
## Authenticate User
- URL: POST /api/auth/login
- Authentication: Not required

### Request

```json
{
  "username": "daniel",
  "password": "password123"
}
```

### Response

```json
{
  "token": "AaaAAaaAAa..."
}
```

The returned token represents the authenticated user and includes:

User identity
Token issue time
Token expiration time

# Using the JWT Token
For all protected endpoints, the client must include the JWT in the HTTP Authorization header.

## Header Format

Authorization: Bearer <JWT_TOKEN>

Requests without a valid token will be rejected.

# Protected Endpoints
All endpoints under the following paths require authentication:

/api/accounts/**
/api/transactions/**

Endpoints under /api/auth/** are publicly accessible.

# Token Validation
For every incoming request to a protected endpoint:

The JWT is extracted from the Authorization header
The token signature is verified
The token expiration date is checked
The user identity is loaded from the token
The request is allowed to proceed if the token is valid

If validation fails, the server responds with:

401 Unauthorized – Missing, invalid, or expired token

# Authentication Error Response Example

```json
{
  "timestamp": "2026-05-04T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid or expired JWT token"
}
```
