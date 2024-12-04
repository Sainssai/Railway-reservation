# Train Reservation Management System

## Description

The **Train Reservation Management System** is a Java-based application that interacts with a PostgreSQL database to manage train passenger reservations. It supports functionalities like adding, deleting, and viewing passenger details using a menu-driven interface.

### Features
- **Insert Passenger Details**: Add new reservations with a unique auto-generated PNR number.
- **Delete Passenger Details**: Remove reservations by providing the PNR number.
- **View All Passengers**: Display details of all passengers in the database.
- **View Passenger by PNR**: Retrieve and display details of a passenger by their PNR number.
- **Exit**: Quit the application.

---

## Technology Stack
- **Programming Language**: Java
- **Database**: PostgreSQL
- **Libraries**: JDBC

---

## Database Table Structure

The application uses a PostgreSQL table with the following schema:

```sql
CREATE TABLE reservation (
    pnrnumber INT PRIMARY KEY,
    passengername VARCHAR(100),
    trainno VARCHAR(10),
    classtype VARCHAR(50),
    dateofjourney DATE,
    startingpoint VARCHAR(100),
    destinationpoint VARCHAR(100)
);
