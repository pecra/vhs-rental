# REST API for VHS rental service - managing rentals,users and reviews.

## **Running the application**

 ### **Locally**
   
   #### Requirements:
   **Java 17+**
   **Maven 3.8+**

  #### **Start the server**


   mvn spring-boot:run
   (app runs at http://localhost:8081)

   Database is initialized with data.sql and Hibernate schema generation,
   but can be accessed through console:
   
   #### **H2 Database Console**
   
   http://localhost:8081/h2-console


   JDBC URL: jdbc:h2:mem:vhsdb
   
   User: sa
   
   Password: (empty)

 ### **DOCKER**

   **Build application JAR**

   mvn clean package -DskipTests
   
   **Build docker image**
   
   docker build -t vhs-rental .

   **Run container**
   
   docker run -p 8081:8081 vhs-rental

   **H2 database console**
   
   http://localhost:8081/h2-console


   JDBC URL: jdbc:h2:mem:vhsdb
   
   User: sa
   
   Password: (empty)

## **Running tests**

To run all tests:
**mvn test**

To run tests without building the whole application:
**mvn -DskipITs test**

## **Business logic**

A VHS can be rented multiple times in a day but **only after it has been returned.**
A rental is considered active when returnDate == null

This ensures that the same VHS cannot be simultaneously rented by multiple users
once it is returned, it may be rented again immediately, even on the same day.

Every rental has dueDate, when returnih a VHS -> if returned late a fee is applied, daily fee is 1 euro.
DueDate is calculated as rentalDate +2 days except on friday, when it is +3 days since the store doesn't work on Sundays.

After returning VHS, first user on the waiting list should be notified (currently implemented to send notification in the log file)

Only VHS and waitlist entries can be deleted, deleting VHS deletes its reviews and waitlist and is possible only if there isn't an active rental.

## **API endpoints**

**VHS**
| Method | Endpoint | Description |
| --- | --- | --- |
| GET | /api/vhs | List all VHS tapes |
| GET | /api/vhs/{id} | Get tape by ID |
| GET | /api/vhs/{id}/details | Details + average rating |
| POST | /api/vhs | Add new VHS |
| DELETE | /api/vhs/{id} | Delete VHS |


**Users**

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | /api/users | List users |
| GET | /api/users/{id} | Get user |
| GET | /api/users/find?email= | Find by email |
| POST | /api/users | Create user |

**Rentals**

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | /api/rentals | List all rentals |
| GET | /api/rentals/{id} | Get rental |
| GET | /api/rentals/active | List active rentals |
| GET | /api/rentals/user/{id} | List rentals for user |
| POST | /api/rentals | Create rental |
| POST | /api/rentals/return/{id} | Return VHS |

**Reviews**

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | /api/reviews/vhs/{id} | Reviews for VHS |
| POST | /api/reviews | Add review (one per user per VHS) |

**Waitlist**
| Method | Endpoint | Description |
| --- | --- | --- |
| GET | /api/waitlist/vhs/{id} | Get waitlist for VHS |
| POST | /api/waitlist | Add new waitlist entry |
| DELETE | /api/waitlist/{id} | Delete waitlist entry |


## **Postman Collection**

A Postman collection named vhs-collection.json is included in the root.

## **Technologies used**

Java 17

Spring Boot

Spring Web

Spring Data JPA

H2 Database

Hibernate

Bean Validation (Jakarta)

Docker

Lombok

Postman

JUnit 
