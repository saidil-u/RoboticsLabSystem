package kg.alatoo.tasktracker.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    // SQLite сохранит всю базу данных в локальный файл tasktracker.db прямо в папке проекта
    private static final String URL = "jdbc:sqlite:tasktracker.db";

    public static Connection getConnection() throws SQLException {
        // Для SQLite не нужны логины, пароли и проверка пользователей
        return DriverManager.getConnection(URL);
    }

    public static void initDatabase() {
        // SQL-запросы для SQLite (автоматическое создание таблиц)
        String createSql = """
                CREATE TABLE IF NOT EXISTS researchers (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    full_name TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE,
                    specialization TEXT NOT NULL
                );
                CREATE TABLE IF NOT EXISTS lab_categories (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE
                );
                CREATE TABLE IF NOT EXISTS lab_projects (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE,
                    category_id INTEGER NOT NULL,
                    lead_id INTEGER NOT NULL,
                    FOREIGN KEY (category_id) REFERENCES lab_categories(id),
                    FOREIGN KEY (lead_id) REFERENCES researchers(id)
                );
                CREATE TABLE IF NOT EXISTS experiments (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    status TEXT NOT NULL,
                    priority INTEGER NOT NULL,
                    experiment_date TEXT NOT NULL,
                    project_id INTEGER NOT NULL,
                    FOREIGN KEY (project_id) REFERENCES lab_projects(id) ON DELETE CASCADE
                );
                CREATE TABLE IF NOT EXISTS experiment_logs (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    experiment_id INTEGER NOT NULL,
                    researcher_id INTEGER NOT NULL,
                    hours_spent REAL NOT NULL DEFAULT 0,
                    UNIQUE (experiment_id, researcher_id),
                    FOREIGN KEY (experiment_id) REFERENCES experiments(id) ON DELETE CASCADE,
                    FOREIGN KEY (researcher_id) REFERENCES researchers(id) ON DELETE CASCADE
                );
                """;

        // Начальные данные для заполнения
        String seedSql = """
                INSERT INTO researchers (full_name, email, specialization) VALUES
                ('Saidilbek Ubaidullaev', 'saidilbek@alatoo.edu.kg', 'Robotics & Control Systems'),
                ('Baibol Sulaimanov', 'baibol@alatoo.edu.kg', 'Embedded Systems'),
                ('Samir Murakaev', 'samir@alatoo.edu.kg', 'Sensor Calibration');

                INSERT INTO lab_categories (name) VALUES
                ('Autonomous Navigation'),
                ('Fluid Dynamics & Sensors');

                INSERT INTO lab_projects (name, category_id, lead_id) VALUES
                ('ESP32 Ultrasonic Slosh Logger', 2, 1),
                ('Autonomous Rover Vision', 1, 2);

                INSERT INTO experiments (title, status, priority, experiment_date, project_id) VALUES
                ('HC-SR04 Low-Pass Filter Calibration', 'COMPLETED', 5, '2026-11-10', 1),
                ('Real-Time Wave Amplitude Logging', 'RUNNING', 5, '2026-11-15', 1),
                ('PID Motor Controller Tuning', 'PLANNED', 4, '2026-11-20', 2),
                ('LiDAR Obstacle Avoidance Test', 'RUNNING', 5, '2026-11-18', 2);

                INSERT INTO experiment_logs (experiment_id, researcher_id, hours_spent) VALUES
                (1, 1, 4.5),
                (2, 1, 5.0),
                (2, 3, 3.0),
                (3, 2, 2.0),
                (4, 1, 6.0);
                """;

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Включаем поддержку внешних ключей в SQLite
            stmt.execute("PRAGMA foreign_keys = ON;");
            stmt.execute(createSql);
            
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM researchers")) {
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.execute(seedSql);
                }
            }
            System.out.println("SQLite Database initialized successfully!");
        } catch (SQLException e) {
            System.out.println("Database init notice: " + e.getMessage());
        }
    }
}