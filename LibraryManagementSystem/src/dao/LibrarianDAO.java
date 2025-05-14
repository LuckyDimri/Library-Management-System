package dao;

import model.Librarian;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibrarianDAO {

    // Add a new librarian (and user)
    public boolean addLibrarian(Librarian librarian, String username, String password) {
        String userSql = "INSERT INTO users (username, password, role, status) VALUES (?, ?, 'LIBRARIAN', 'ACTIVE')";
        String librarianSql = "INSERT INTO librarians (user_id, name, email, phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            // Insert user
            int userId = -1;
            try (PreparedStatement userStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, username);
                userStmt.setString(2, password);
                userStmt.executeUpdate();
                ResultSet rs = userStmt.getGeneratedKeys();
                if (rs.next()) userId = rs.getInt(1);
            }

            // Insert librarian
            try (PreparedStatement librarianStmt = conn.prepareStatement(librarianSql)) {
                librarianStmt.setInt(1, userId);
                librarianStmt.setString(2, librarian.getName());
                librarianStmt.setString(3, librarian.getEmail());
                librarianStmt.setString(4, librarian.getPhone());
                librarianStmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all librarians
    public List<Librarian> getAllLibrarians() {
        List<Librarian> list = new ArrayList<>();
        String sql = "SELECT * FROM librarians";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Librarian l = new Librarian();
                l.setId(rs.getInt("id"));
                l.setUserId(rs.getInt("user_id"));
                l.setName(rs.getString("name"));
                l.setEmail(rs.getString("email"));
                l.setPhone(rs.getString("phone"));
                list.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Delete a librarian (and user)
    public boolean deleteLibrarian(int librarianId, int userId) {
        String sql1 = "DELETE FROM librarians WHERE id = ?";
        String sql2 = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1);
                 PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                stmt1.setInt(1, librarianId);
                stmt1.executeUpdate();
                stmt2.setInt(1, userId);
                stmt2.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}