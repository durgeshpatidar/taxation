# Microservices Project - API Overview

This project consists of multiple independent microservices, each responsible for a specific domain in a client-taxation system. The services are loosely coupled and communicate as needed.

## Technologies & Dependencies

- **Programming Language:** Java 21
- **Framework:** Spring Boot 3.x
- **Build Tool:** Maven
- **Inter-Service Communication:** REST APIs, Spring Cloud OpenFeign (for Feign clients)
- **API Documentation:** Springdoc OpenAPI / Swagger
- **Database:**
  - **Default:** H2 in-memory database for rapid development and testing (auto-configured)
- **JPA:** Spring Data JPA with Hibernate
- **Testing:** JUnit 5, Mockito
- **Common Config:** Shared Swagger/OpenAPI configuration via a dedicated microservice or shared library
- **Version Control:** Git, GitHub
---

## 1. Client Service

Manages client data of various types (Salaried, Business, House).

**Key APIs:**

- `POST /clients` - Create a new client
- `GET /clients/{id}` - Get client by ID
- `GET /clients` - List all clients
- `PUT /clients/{id}` - Update client
- `DELETE /clients/{id}` - Delete client

---

## 2. Salaried Service

Handles tax filing and management for salaried clients.

**Key APIs:**

- `POST /salaried/tax-file` - File salaried tax (parameters: clientId, salary, taxSubType)
- `GET /salaried/tax-total` - Get total tax paid by salaried client

Uses the Taxation Service for tax filing.

---

## 3. Business Service

Handles tax filing for business clients (e.g., GST, corporate tax).

**Key APIs:**

- `POST /business/tax-file` - File business tax (parameters: clientId, revenue, taxSubType)
- `GET /business/tax-total` - Get total tax paid by business client

Uses the Taxation Service for tax filing.

---

## 4. House Service

Manages taxation related to houses (property tax, etc.)

**Key APIs:**

- `POST /house/tax-file` - File house tax (parameters: clientId, propertyValue, taxSubType)
- `GET /house/tax-total` - Get total tax paid related to the house

Uses the Taxation Service for tax filing.

---

## 5. Taxation Service

Central service for managing tax records and calculations.

**Key APIs:**

- `POST /taxations` - Submit a tax record
- `GET /taxations/{id}` - Get tax record by ID
- `GET /taxations/client/{clientId}` - List tax records by client
- `GET /taxations/total/{clientId}` - Get total tax paid by client

---

## 6. Common Config Service

Provides shared configurations like Swagger/OpenAPI setup to be reused by all microservices.
can be used for further common configurations.

---

## Notes:

- All services run independently on different ports.
- Services communicate via REST APIs (some use Feign clients).
- Swagger UI is available for each service at `/swagger-ui.html` for API documentation and testing.
- Use appropriate client types and parameters to file taxes.
- The taxation service acts as the central hub for tax data persistence.

---

## Running the Project

- Configure each service’s `application.properties` with distinct ports.
- Start services in any order (they are loosely coupled).
- Use Swagger or API clients (Postman, curl) to interact.

---

*For detailed API specs, refer to Swagger UI on each service.*

---

