
# ✈️ Airline Management System

A production-grade **Spring Boot + MySQL** application for managing airline operations, including flight bookings, seat selection, payments, cancellations, passenger details, and admin functionalities.

---

## 📦 Tech Stack

- **Backend**: Java 21, Spring Boot 3.4.3
- **Database**: MySQL
- **Security**: Spring Security, JWT
- **API Docs**: Swagger / OpenAPI (Springdoc)
- **Build Tool**: Maven

---

## 📁 Project Structure

```
com.airline
├── controller             # REST Controllers
├── service
│   ├── impl               # Business logic
│   └── strategy           # Payment/refund strategy pattern
├── repository             # JPA Repositories
├── entity                 # JPA Entities
├── dto                    # Request/Response Models
├── config                 # Security, Swagger, etc.
├── exception              # Global exception handling
├── util                   # Utility classes
└── security               # JWT filters, token provider
```

---

## ✅ Features

- 🔍 Search Flights (by source, destination, date)
- 🛫 Book Flights with Seat Selection
- 💳 Payment Gateway Integration (Strategy Pattern)
- 👥 Passenger & Baggage Info
- 🔄 Booking Cancellation + Refunds (Strategy Pattern)
- 👩‍✈️ Admin Panel: Create Flights, Assign Aircraft & Crew
- 📊 Admin View of All Passengers per Flight
- 🧾 Booking Summary API (All details)
- 🔐 JWT Authentication + Role-Based Access

---

## 🚀 Getting Started

### Prerequisites

- Java 21
- MySQL DB running locally
- Maven 3.8+

### Clone & Setup

```bash
git clone https://github.com/akash-giri/airlinemanagementSystem.git
cd airlinemanagementSystem
```

### Configure MySQL

Update `application.yml` or `application.properties` with your local DB config.

### Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🔐 Authentication

- Register via `/auth/register`
- Login to receive JWT via `/auth/login`
- Use `Bearer <token>` in `Authorization` header for secured routes

---

## 📬 API Testing

Use the provided Postman collection: [📥 Download Postman Collection](./Airline_Management_Postman_Collection.json)

---

## 🧠 Design Principles

- ✅ Follows **SOLID principles**
- ✅ Modular code with **Service**, **Strategy**, and **DTO** layers
- ✅ Uses **JWT** for stateless authentication

---

## 📄 License

MIT License — Feel free to fork and enhance!

---

> Built with ❤️ by Akash Giri  
