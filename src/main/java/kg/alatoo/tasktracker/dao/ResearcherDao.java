package kg.alatoo.tasktracker.dao;

import kg.alatoo.tasktracker.config.DatabaseConfig;
import kg.alatoo.tasktracker.model.Researcher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResearcherDao {
    public List<Researcher> findAll() {
        List<Researcher> list = new ArrayList<>();
        String sql = "SELECT id, full_name, email, specialization FROM researchers ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Researcher(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("specialization")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return list;
    }

    public void create(String fullName, String email, String specialization) {
        String sql = "INSERT INTO researchers (full_name, email, specialization) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            stmt.setString(2, email);
            stmt.setString(3, specialization);
            stmt.executeUpdate();
            System.out.println("Researcher added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding researcher: " + e.getMessage());
        }
    }

    public void updateSpecialization(int id, String newSpec) {
        String sql = "UPDATE researchers SET specialization = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newSpec);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Specialization updated." : "Researcher not found.");
        } catch (SQLException e) {
            System.out.println("Error updating researcher: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM researchers WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Researcher deleted." : "Researcher not found.");
        } catch (SQLException e) {
            System.out.println("Error deleting researcher: " + e.getMessage());
        }
    }
}
