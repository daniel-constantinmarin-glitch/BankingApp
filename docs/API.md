# API Documentation

This document describes the REST APIs exposed by the Mini Banking Application.
All secured endpoints require a valid JWT token.


# Authentication

Authentication is based on JWT (JSON Web Token).

# Login
- Endpoint: POST /api/auth/login
- Description: Authenticates a user and returns a JWT token.

# Request
```json
{
  "username": "daniel",
  "password": "password123"
}
```
# Response

```json
{
"token": "AaAAaaAaa..."
}
```
The token must be included in the Authorization header for protected endpoints:

Authorization: Bearer <token>

# 3. User / Account Endpoints
Document each endpoint separately.


# Account APIs

## Create Account
- URL: POST /api/accounts
- Authentication: Required

### Request
```json
{
  "ownerName": "Daniel Marin",
  "initialBalance": 1000
}
```
### Response
```json
{
  "id": 1,
  "ownerName": "Daniel Marin",
  "balance": 1000
}
```

## Get Account by ID
- URL: GET /api/accounts/{id}
- Authentication: Required

### Request
GET /api/accounts/1

### Response
```json
{
  "id": 1,
  "ownerName": "John Doe",
  "balance": 1000
}
```
or

404 Not Found – Account not found

## List Accounts
- URL: GET /api/accounts
- Authentication: Required

### Request
GET /api/accounts

### Response

```json
[
  {
    "id": 1,
    "ownerName": "Daniel Marin",
    "balance": 1000
  },
  {
    "id": 2,
    "ownerName": "Marin Daniel",
    "balance": 500
  }
]
```

## Deposit Money
- URL: POST /api/accounts/{id}/deposit
- Authentication: Required

### Request
```json
{
"amount": 200
}
```
### Response
```json
{
  "id": 1,
  "ownerName": "Daniel Marin",
  "balance": 1200
}
```
or

400 Bad Request – Invalid amount
404 Not Found – Account not found

## Withdraw Money
- URL: POST /api/accounts/{id}/withdraw
- Authentication: Required

### Request
```json
{
  "amount": 300
}
```

### Response
```json
{
  "id": 1,
  "ownerName": "John Doe",
  "balance": 700
}
```
or

400 Bad Request – Insufficient balance
404 Not Found – Account not found

### Transfer Money
- URL: POST /api/accounts/transfer
- Authentication: Required

### Request 
```json
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 250
}
```

### Response
```json
{
  "fromAccount": {
    "id": 1,
    "balance": 750
  },
  "toAccount": {
    "id": 2,
    "balance": 750
  }
}
```
or
 
400 Bad Request – Insufficient balance or invalid amount
404 Not Found – One or both accounts not found

