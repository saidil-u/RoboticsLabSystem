# Robotics Lab Management System

A console-based Java application for managing robotics research projects, researchers, and laboratory experiments using SQLite (3NF) and JDBC.

## 🚀 Features
- **Full CRUD Operations**: Complete management for Researchers (`researchers`) and Laboratory Experiments (`experiments`).
- **Relational Integrity (3NF)**: 5 normalized tables with Foreign Key constraints and cascading deletes.
- **Advanced SQL JOIN Search**: Multi-table search combining experiments, projects, and categories (`researchers`, `lab_categories`, `lab_projects`, `experiments`, `experiment_logs`).
- **Analytics & Aggregates**: Automated reporting using `GROUP BY`, `COUNT`, `AVG`, and `SUM` to track total lab hours and project priorities.
- **Safe Database Access**: Robust data handling via `PreparedStatement` and `try-with-resources` to prevent SQL injections.

---

## 🗄️ Database Schema (3NF)
The database consists of 5 related tables:
1. `researchers` — Lab members, contact emails, and specializations.
2. `lab_categories` — Research focus areas (e.g., Autonomous Navigation, Fluid Dynamics).
3. `lab_projects` — Projects linked to categories and led by specific researchers.
4. `experiments` — Individual tests/experiments with priorities, statuses, and dates.
5. `experiment_logs` — Junction table (M:N) tracking researchers' hours spent on specific experiments.

---

## 🛠️ Tech Stack
- **Language**: Java 17+
- **Database**: SQLite (`sqlite-jdbc`)
- **Build Tool**: Maven
- **Architecture**: DAO Pattern (Data Access Object) + Modular Layering

---

## ⚙️ How to Run
1. Ensure you have **Java 17+** and **Maven** installed.
2. Open the project folder in **VS Code** (or IntelliJ IDEA).
3. The project will automatically initialize the local SQLite database (`tasktracker.db`) and seed initial test data upon the first run.
4. Run the application from `Main.java`.