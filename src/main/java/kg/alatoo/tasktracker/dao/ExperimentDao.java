package kg.alatoo.tasktracker.dao;

import kg.alatoo.tasktracker.config.DatabaseConfig;
import kg.alatoo.tasktracker.model.Experiment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExperimentDao {
    public List<Experiment> findAll() {
        List<Experiment> list = new ArrayList<>();
        String sql = "SELECT id, title, status, priority, experiment_date, project_id FROM experiments ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Experiment(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("status"),
                        rs.getInt("priority"),
                        rs.getDate("experiment_date"),
                        rs.getInt("project_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return list;
    }

    public void create(String title, String status, int priority, Date date, int projectId) {
        String sql = "INSERT INTO experiments (title, status, priority, experiment_date, project_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, status);
            stmt.setInt(3, priority);
            stmt.setDate(4, date);
            stmt.setInt(5, projectId);
            stmt.executeUpdate();
            System.out.println("Experiment created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating experiment: " + e.getMessage());
        }
    }

    public void updateStatus(int id, String status) {
        String sql = "UPDATE experiments SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Status updated." : "Experiment not found.");
        } catch (SQLException e) {
            System.out.println("Error updating status: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM experiments WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Experiment deleted." : "Experiment not found.");
        } catch (SQLException e) {
            System.out.println("Error deleting experiment: " + e.getMessage());
        }
    }

    public void searchByKeyword(String keyword) {
        String sql = """
                SELECT e.id, e.title, e.status, p.name AS project_name, c.name AS category_name
                FROM experiments e
                JOIN lab_projects p ON e.project_id = p.id
                JOIN lab_categories c ON p.category_id = c.id
                WHERE LOWER(e.title) LIKE LOWER(?) OR LOWER(p.name) LIKE LOWER(?)
                ORDER BY e.id
                """;
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n--- Search Results (JOIN Query) ---");
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.printf("ID: %d | Experiment: %-32s | Status: %-10s | Project: %-28s | Category: %s%n",
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("status"),
                            rs.getString("project_name"),
                            rs.getString("category_name"));
                }
                if (!found) {
                    System.out.println("No matching experiments found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Search error: " + e.getMessage());
        }
    }

    public void printAnalyticsReport() {
        String sql = """
                SELECT p.name AS project_name,
                       COUNT(DISTINCT e.id) AS total_experiments,
                       ROUND(AVG(e.priority), 2) AS avg_priority,
                       COALESCE(SUM(l.hours_spent), 0) AS total_hours
                FROM lab_projects p
                LEFT JOIN experiments e ON p.id = e.project_id
                LEFT JOIN experiment_logs l ON e.id = l.experiment_id
                GROUP BY p.id, p.name
                ORDER BY total_hours DESC
                """;
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("\n--- Lab Project Analytics Report (GROUP BY + Aggregates) ---");
            while (rs.next()) {
                System.out.printf("Project: %-30s | Experiments: %d | Avg Priority: %.2f | Total Hours: %.2f%n",
                        rs.getString("project_name"),
                        rs.getInt("total_experiments"),
                        rs.getDouble("avg_priority"),
                        rs.getDouble("total_hours"));
            }
        } catch (SQLException e) {
            System.out.println("Report error: " + e.getMessage());
        }
    }
}
