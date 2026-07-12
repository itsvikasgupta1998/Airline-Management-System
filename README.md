# ✈️ Airline Management System

A production-ready **Airline Management System** built using **Java 17**, **Spring Boot**, **Spring Security**, **JWT Authentication**, **MySQL**, and **Spring Data JPA**.

The application provides a complete airline booking workflow including authentication, flight management, passenger management, seat allocation, booking, payments, ticket generation (PDF), email notifications, refunds, search APIs, pagination, filtering, and role-based access control.

---

# 🚀 Features

## 🔐 Authentication & Authorization

- JWT Authentication
- User Registration & Login
- BCrypt Password Encryption
- Role-Based Access Control (ADMIN / USER)
- Secure REST APIs using Spring Security

---

## 👤 User Management

- Create User
- Update User
- Delete User (Soft Delete)
- Restore User
- Search Users
- Pagination & Sorting

---

## 🛫 Airport Management

- Create Airport
- Update Airport
- Delete Airport
- Restore Airport
- Search Airports
- Pagination & Sorting

---

## ✈️ Aircraft Management

- Create Aircraft
- Update Aircraft
- Delete Aircraft
- Restore Aircraft
- Search Aircraft
- Pagination & Sorting

---

## 🗓 Flight Management

- Create Flight
- Update Flight
- Delete Flight
- Restore Flight
- Search Flights
- Filter Flights
- Pagination & Sorting

---

## 💺 Seat Management

- Seat Allocation
- Travel Class Validation
- Seat Availability Validation
- Seat Booking Status
- Search Seats
- Pagination & Sorting

---

## 👨‍👩‍👧 Passenger Management

- Create Passenger
- Update Passenger
- Delete Passenger
- Restore Passenger
- Passport Validation
- Search Passengers

---

## 📖 Booking Management

- Flight Booking
- Booking Reference Generation
- Seat Reservation
- Fare Calculation
- Tax Calculation
- Service Fee
- Booking Cancellation
- Booking Restoration
- Booking Search
- Booking History
- Pagination & Sorting
- Business Validations

---

## 💳 Payment Management

- Payment Processing
- Duplicate Payment Validation
- Transaction Id Generation
- Gateway Reference Generation
- Payment Status
- Refund Processing
- Partial Business Validations
- Payment Search
- Pagination & Sorting

---

## 🎫 Ticket Management

- Automatic Ticket Generation
- PDF Ticket Generation
- QR Code Generation
- Download Ticket
- Resend Ticket
- Cancel Ticket
- Ticket Search
- Pagination & Sorting

---

## 🔔 Notification Management

- Create Notification
- Update Notification
- Delete Notification
- Restore Notification
- Send Notification
- Mark Notification as Read
- Email Notifications
- Notification Status Tracking
- Automatic Notifications

Automatic Notifications:

- Booking Confirmation
- Payment Success
- Refund Processed

Manual Notifications:

- Admin creates notification
- Notification remains **PENDING**
- Admin sends notification manually

---

## 📧 Email Integration

- Booking Confirmation Email
- Payment Success Email
- Refund Email
- HTML Email Support
- Ticket PDF Attachment
- JavaMailSender Integration

---

## 🔍 Search APIs

Dynamic search using Spring Data JPA Specification.

Supports

- Multiple Filters
- Pagination
- Sorting
- Dynamic Queries

---

## 🛡 Exception Handling

- Global Exception Handler
- Custom Exceptions
- Validation Errors
- Business Exceptions
- Resource Not Found Handling

---

## ✅ Validations

- Bean Validation
- Business Rule Validation
- Duplicate Record Validation
- Seat Availability Validation
- Passport Expiry Validation
- Flight Departure Validation
- Payment Validation
- Refund Validation

---

## 📊 API Documentation

Swagger / OpenAPI Integration

```
http://localhost:8080/swagger-ui/index.html
```

---

# 🏗 Tech Stack

| Technology | Version |
|------------|----------|
| Java | 17 |
| Spring Boot | 3.x |
| Spring Security | Latest |
| JWT | Latest |
| Spring Data JPA | Latest |
| Hibernate | Latest |
| MySQL | 8+ |
| Maven | Latest |
| Lombok | Latest |
| MapStruct | Latest |
| Swagger (SpringDoc) | Latest |
| OpenHTMLToPDF | Latest |
| JavaMailSender | Latest |

---

# 📂 Project Structure

```
src/main/java
│
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── enums
├── exception
├── mapper
├── repository
├── security
├── service
│   ├── impl
│   └── interfaces
├── specification
├── utils
└── AirlineManagementApplication
```

---

# 🏛 Architecture

```
Client

↓

Controller

↓

Service Layer

↓

Repository Layer

↓

MySQL Database
```

---

# 🔄 Booking Flow

```
User Login

↓

Search Flight

↓

Select Seat

↓

Create Booking

↓

Payment

↓

Generate Ticket PDF

↓

Send Notification

↓

Download Ticket
```

---

# 🔒 Security

- JWT Authentication
- Stateless Session
- Password Encryption
- Protected APIs
- Role-Based Authorization

---

# 📈 Business Validations

- Prevent duplicate booking
- Prevent duplicate payment
- Seat availability validation
- Travel class validation
- Passport expiry validation
- Flight departure validation
- Booking status validation
- Payment status validation
- Refund amount validation
- Ticket generation only after successful payment

---

# ⚡ Project Highlights

- Layered Architecture
- RESTful APIs
- Production-ready Code Structure
- DTO Pattern
- Mapper Layer (MapStruct)
- JPA Specifications
- Soft Delete Support
- Global Exception Handling
- Pagination
- Sorting
- Filtering
- Email Notifications
- Ticket PDF Generation
- QR Code Generation
- Clean Code
- SOLID Principles

---

# 🛠 Getting Started

## Clone Repository

```bash
git clone https://github.com/itsvikasgupta1998/airline-management-system.git
```

---

## Configure Database

Update

```
application.properties
```

```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.mail.username=
spring.mail.password=
```

---

## Run Application

```
mvn clean install

mvn spring-boot:run
```

---

# 📖 API Documentation

Swagger

```
http://localhost:8080/swagger-ui/index.html
```

---

# 👨‍💻 Author

**Vikas Gupta**

Java Backend Developer

Java • Spring Boot • Spring Security • JWT • Hibernate • MySQL • REST APIs
