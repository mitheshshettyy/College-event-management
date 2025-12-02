# 📝 College Event Management System

This project is a web application designed to manage college events efficiently. It provides a secure interface where **Admins** can handle the complete lifecycle of events (creation, modification, and deletion), and **Students** can easily register for and track the events they plan to attend.

---

## ✨ Key Features

* **Admin Management:**
    * Add new events with details like date, time, and description.
    * Update existing event details.
    * Delete events from the system.
    * View a complete list of student registrations for any specific event.
* **Student Functionality:**
    * View a list of all available events.
    * Securely register for any listed event.
    * View a personalized list of their currently registered events.
* **Authentication:** Secure user and admin login/registration mechanisms.

---

## 🛠️ Tech Stack

This application is built using a modern, popular Java ecosystem stack:

| Category | Technology | Description |
| :--- | :--- | :--- |
| **Backend** | **Spring Boot** (Java) | Used to build a robust, production-ready server-side application, utilizing the power of Java for business logic. |
| **Frontend Rendering** | **Java (Thymeleaf)** | Used for server-side template rendering, integrating seamlessly with the Spring Boot MVC structure to generate dynamic HTML views. |
| **Styling** | **Tailwind CSS** | A utility-first CSS framework for rapidly and consistently styling the user interface. |
| **Database** | **MySQL** | A relational database used for persistence of all data (Events, Students, Admins, and Registrations). |
| **Build Tool** | **Maven** | Used for dependency management and project build automation. |

---

## 📂 Project File Structure

The project follows a standard **Spring Boot/Maven** convention and adheres to the **Model-View-Controller (MVC)** architectural pattern.

The core application logic is located within the `src/main/java/com.example.demo` package, organized into four primary directories:

Project11/
├── src/main/java/com.example.demo/
│   ├── com.example.demo.controller/  
│   │   ├── AdminViewController.java  // 🔐 Handles Admin-specific event/registration views and logic
│   │   ├── AuthController.java       // 🔑 Handles user/admin login and registration
│   │   ├── EventController.java      // 🎟️ Handles common event viewing logic
│   │   ├── StudentController.java    // 🧑‍🎓 Handles Student-specific registration views and logic
│   │   └── ViewController.java       // 🏠 Handles general application views (e.g., home page)
│   ├── com.example.demo.model/      
│   │   ├── Admin.java                // 👤 Entity representing an Administrator
│   │   ├── Department.java           // 🏢 Entity for departments (if applicable)
│   │   ├── Event.java                // 🗓️ Core Entity representing an event
│   │   ├── Registration.java         // ✅ Entity linking Students to Events
│   │   └── Student.java              // 🧑‍🎓 Entity representing a Student
│   ├── com.example.demo.repository/  
│   │   ├── IAdminRepo.java           // 💾 JpaRepository for Admin data operations
│   │   ├── IEventRepo.java           // 💾 JpaRepository for Event data operations
│   │   ├── IRegistrationRepo.java    // 💾 JpaRepository for Registration data operations
│   │   └── IStudentRepo.java         // 💾 JpaRepository for Student data operations
│   ├── com.example.demo.service/     
│   │   ├── EventService.java         // ⚙️ Business logic for Event management
│   │   └── StudentService.java       // ⚙️ Business logic for Student management (e.g., registration)
│   └── Project111Application.java    // 🚀 Main Spring Boot entry point
├── src/main/resources/
│   ├── static/                       // 🖼️ Stores static assets (CSS, JS, images, compiled Tailwind CSS)
│   │   └── assets/                   
│   ├── templates/                    // 📄 Stores Thymeleaf HTML files (Views)
│   └── application.properties        // ⚙️ Application configuration (e.g., database connection, port)
└── ...
