# FitTrack API 🏃‍♀️

A Strava-inspired running tracker REST API built with Java Spring Boot, MySQL and JWT authentication.

## Features
- User registration and login with JWT authentication
- Log running activities (route, distance, duration, date)
- Auto pace calculation (min/km)
- Personal stats — total distance, average pace, best run
- Secured endpoints with Spring Security

## Tech Stack
- Java 17
- Spring Boot 3.5
- Spring Security + JWT
- MySQL 8.0
- Maven

## API Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | /api/auth/register | Register a new user | No |
| POST | /api/auth/login | Login and get JWT token | No |
| POST | /api/runs/log | Log a new run | Yes |
| GET | /api/runs/history | Get all your runs | Yes |
| GET | /api/runs/stats | Get personal stats | Yes |
| DELETE | /api/runs/{id} | Delete a run | Yes |

## Setup & Run

1. Clone the repository
2. Create a MySQL database named `fittrack`
3. Update `application.properties` with your MySQL credentials
4. Run `mvn spring-boot:run`
5. API runs on `http://localhost:8080`

## Sample Request

Register a user:
```json
POST /api/auth/register
{
    "username": "malini",
    "email": "malini@email.com",
    "password": "yourpassword"
}
```

Log a run:
```json
POST /api/runs/log
{
    "routeName": "Gachibowli Stadium",
    "distanceKm": "5.0",
    "durationMinutes": "32",
    "runDate": "2026-04-19"
}
```

## Author
Malini Srinivasa Moorthy — [LinkedIn](https://www.linkedin.com/in/malini-srinivasamoorthy)
