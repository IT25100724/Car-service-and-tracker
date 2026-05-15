# Car Service Tracking System 🚗🔧

A comprehensive, full-stack web application designed to streamline car service management for garages and their customers.

## 🌟 Key Features

### 👤 User Management
- **Role-Based Access Control**: Separate dashboards for **Administrators**, **Staff Members**, and **Customers**.
- **Secure Profiles**: Manage contact details, addresses, and account settings.

### 🚗 Vehicle & Mileage Tracking
- **Digital Garage**: Customers can register and manage multiple vehicles.
- **Smart Maintenance**: Automated tracking of **Current Mileage** and **Next Service Mileage** to ensure vehicles are serviced on time.

### 📅 Booking & Service Workflow
- **Service Catalog**: Browse available services with transparent pricing and duration.
- **Live Tracking**: Track booking status from `PENDING` → `CONFIRMED` → `IN_PROGRESS` → `COMPLETED`.
- **Mileage Updates**: Automatic mileage synchronization upon service completion.

### 💬 Feedback & Reports
- **Customer Reviews**: Rate services (1-5 stars) and leave detailed feedback.
- **Admin Analytics**: Real-time stats on total revenue, active bookings, and team performance.

---

## 🛠️ Technology Stack

- **Backend**: Java 17+, Spring Boot, Spring Data JPA, Hibernate, Lombok
- **Database**: MySQL 8.0
- **Frontend**: HTML5, Vanilla CSS3 (Custom Design System), JavaScript (ES6+), FontAwesome Icons
- **Architecture**: Service-Oriented Architecture (SOA) with RESTful APIs

---

## 🚀 Getting Started

### Prerequisites
- JDK 17 or higher
- MySQL Server
- Maven

### Installation
1. **Clone the repository**:
   ```bash
   git clone <your-repository-url>
   ```

2. **Database Setup**:
   - Create a database named `car_service_db`.
   - Execute the SQL commands in `schema.sql` to initialize the tables and sample data.

3. **Configure Database**:
   - Open `src/main/resources/application.properties`.
   - Update `spring.datasource.username` and `spring.datasource.password` with your MySQL credentials.

4. **Run the Application**:
   - Using Maven:
     ```bash
     mvn spring-boot:run
     ```
   - Or run the `CarServiceApplication.java` file directly from your IDE.

5. **Access the Portal**:
   - Open your browser and go to `http://localhost:8080/index.html`.

---

## 👥 Team & Collaboration
This project was built using a modular approach where different services (User, Vehicle, Staff, Booking, etc.) were developed independently and integrated into the core system.

## 📄 License
This project is licensed under the MIT License - see the LICENSE file for details.
