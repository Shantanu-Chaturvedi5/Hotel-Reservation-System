# 🏨 Hotel Reservation System (Java)

A **console-based Hotel Reservation System** built using **Core Java**, demonstrating strong use of **Object-Oriented Programming (OOP)** principles along with **File I/O–based persistence**. The system allows users to **search, book, cancel, and manage hotel room reservations**, complete with **payment simulation**, **room categorization**, and **hotel statistics**.

---

## 📌 Key Features

### 🔍 Room Management

* Room categorization:

  * **Standard**
  * **Deluxe**
  * **Suite**
* View all available rooms
* Search rooms by:

  * Category
  * Price range
* Automatic availability update after booking/cancellation

### 📅 Reservation Management

* Book rooms with:

  * Customer name validation
  * Check-in and check-out date validation
* Cancel existing reservations with **80% refund simulation**
* View:

  * Individual reservation details
  * All reservations
  * Customer booking history

### 💳 Payment Simulation

* Multiple payment methods:

  * Credit Card
  * Debit Card
  * UPI
  * Net Banking
* Promo code support (`WELCOME10` – 10% discount)
* Discount calculation and final amount display

### 📊 Hotel Statistics Dashboard

* Total rooms
* Available vs booked rooms
* Occupancy rate
* Revenue generated
* Available rooms by category

### 💾 Data Persistence (File I/O)

* Data stored locally using text files:

  * `rooms.txt`
  * `reservations.txt`
* Automatic read/write on:

  * Application startup
  * Booking
  * Cancellation
  * Program exit

---

## 🧱 Project Structure

```
HotelReservationSystem/
│
├── Main.java                    # Application entry point & UI logic
├── Room.java                    # Room entity
├── Reservation.java             # Reservation entity
├── FileManager.java             # File I/O handling
├── Payment.java                 # Payment simulation logic
├── ValidationUtil.java          # Input & business validation
├── ReservationIDGenerator.java  # Unique reservation ID generator
│
├── rooms.txt                    # Room data storage
├── reservations.txt             # Reservation data storage
└── README.md                    # Project documentation
```

---

## 🗂 File Details

### `rooms.txt`

Stores all room details in CSV format:

```
RoomNumber,Category,Price,Available
```

Example:

```
101,Standard,2000.0,true
301,Suite,6000.0,false
```

### `reservations.txt`

Stores all booking records:

```
ReservationID,CustomerName,RoomNumber,Category,Price,PaymentStatus,CheckIn,CheckOut,Discount,PaymentMethod
```

Example:

```
RES1001,Shantanu,301,Suite,6000.0,Paid,2026-01-12,2026-01-13,0.0,Debit Card
```

---

## ⚙️ Technologies Used

* **Java (JDK 17+)**
* Core Java APIs:

  * `java.io` (File handling)
  * `java.time` (Date & time)
  * `java.util` (Collections, Scanner)
* No external libraries

---

## 🧠 OOP Concepts Applied

* **Encapsulation** – Room & Reservation classes
* **Abstraction** – Utility classes for validation and file handling
* **Single Responsibility Principle** – Each class has a well-defined role
* **Separation of Concerns** – UI, logic, persistence handled separately

---

## ▶️ How to Run

1. Compile all Java files:

```bash
javac *.java
```

2. Run the application:

```bash
java Main
```

3. Ensure `rooms.txt` exists in the same directory (or it will start empty).

---

## 🧪 Sample Flow

1. View available rooms
2. Search by category or price
3. Book a room
4. Apply promo code & choose payment method
5. Reservation saved to `reservations.txt`
6. Cancel booking → room availability restored

---

## 🚀 Future Enhancements

* GUI (JavaFX / Swing)
* Database integration (MySQL / SQLite)
* Admin panel
* Multiple room booking per customer
* Date-wise availability checking
* Login & authentication

---
