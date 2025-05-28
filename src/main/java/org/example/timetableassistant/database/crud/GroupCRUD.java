package org.example.timetableassistant.database.crud;


import org.example.timetableassistant.database.DatabaseConfig;
import org.example.timetableassistant.database.OperationResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupCRUD {
    private static final String URL = DatabaseConfig.getUrl();
    private static final String USER = DatabaseConfig.getUser();
    private static final String PASSWORD = DatabaseConfig.getPassword();

    public OperationResult insertGroup(int number, String semiyear) {
        assert number > 0 : "number must be greater than 0";
        assert semiyear != null : "semiyear must not be null";
        String query = "INSERT INTO groups (number, semiyear) VALUES (?, ?::semiyear_enum)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, number);
            stmt.setString(2, semiyear);
            stmt.executeUpdate();
            return new OperationResult(true, "Grupa a fost adăugată cu succes.");
        } catch (SQLException e) {
            return new OperationResult(false, "Eroare la inserarea grupei: " + e.getMessage());
        }
    }


    public OperationResult getGroupById(int id) {
        assert id > 0 : "id must be greater than 0";
        String query = "SELECT * FROM groups WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("id", rs.getInt("id"));
                data.put("number", rs.getInt("number"));
                data.put("semiyear", rs.getString("semiyear"));
                return new OperationResult(true, data);
            } else {
                return new OperationResult(false, "Grupa cu ID-ul " + id + " nu a fost găsită.");
            }
        } catch (SQLException e) {
            return new OperationResult(false, "Eroare la obținerea grupei: " + e.getMessage());
        }
    }

    public OperationResult getGroupByNumberAndSemiyear(int number, String semiyear) {
        assert number > 0 : "number must be greater than 0";
        String query = "SELECT * FROM groups WHERE number = ? AND semiyear = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, number);
            stmt.setString(2, semiyear);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("id", rs.getInt("id"));
                data.put("number", rs.getInt("number"));
                data.put("semiyear", rs.getString("semiyear"));
                return new OperationResult(true, data);
            } else {
                return new OperationResult(false, "Grupa " + number + " - " + semiyear + " nu a fost găsită.");
            }
        } catch (SQLException e) {
            return new OperationResult(false, "Eroare la obținerea grupei: " + e.getMessage());
        }
    }

    public OperationResult getAllGroups() {
        String query = "SELECT * FROM groups";
        List<Map<String, Object>> groups = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("id", rs.getInt("id"));
                data.put("number", rs.getInt("number"));
                data.put("semiyear", rs.getString("semiyear"));
                groups.add(data);
            }

            if (groups.isEmpty()) {
                return new OperationResult(false, "Nu există grupe înregistrate.");
            }

            return new OperationResult(true, groups);

        } catch (SQLException e) {
            return new OperationResult(false, "Eroare la obținerea grupelor: " + e.getMessage());
        }
    }



    public OperationResult updateGroup(int id, int newNumber, String newSemiyear) {
        assert id > 0 : "id must be greater than 0";
        assert newNumber > 0 : "newNumber must be greater than 0";
        assert newSemiyear != null : "newSemiyear must not be null";
        String query = "UPDATE groups SET number = ?, semiyear = ?::semiyear_enum WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, newNumber);
            stmt.setString(2, newSemiyear);
            stmt.setInt(3, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return new OperationResult(true, "Grupa a fost actualizată cu succes.");
            } else {
                return new OperationResult(false, "Grupa cu ID-ul " + id + " nu a fost găsită.");
            }
        } catch (SQLException e) {
            return new OperationResult(false, "Eroare la actualizarea grupei: " + e.getMessage());
        }
    }


    public OperationResult deleteGroup(int id) {
        assert id > 0 : "id must be greater than 0";
        String query = "DELETE FROM groups WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return new OperationResult(true, "Grupa a fost ștearsă cu succes.");
            } else {
                return new OperationResult(false, "Grupa cu ID-ul " + id + " nu a fost găsită.");
            }
        } catch (SQLException e) {
            return new OperationResult(false, "Eroare la ștergerea grupei: " + e.getMessage());
        }
    }
}
