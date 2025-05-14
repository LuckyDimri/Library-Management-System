package dao;

import model.Student;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // Add a new student (and user)
    public boolean addStudent(Student student, String username, String password) {
        String userSql = "INSERT INTO users (username, password, role, status) VALUES (?, ?, 'STUDENT', 'ACTIVE')";
        String studentSql = "INSERT INTO students (user_id, name, email, phone) VALUES (?, ?, ?, ?)";
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

            // Insert student
            try (PreparedStatement studentStmt = conn.prepareStatement(studentSql)) {
                studentStmt.setInt(1, userId);
                studentStmt.setString(2, student.getName());
                studentStmt.setString(3, student.getEmail());
                studentStmt.setString(4, student.getPhone());
                studentStmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all students
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setUserId(rs.getInt("user_id"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setPhone(rs.getString("phone"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Delete a student (and user)
    public boolean deleteStudent(int studentId, int userId) {
        String sql1 = "DELETE FROM students WHERE id = ?";
        String sql2 = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1);
                 PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                stmt1.setInt(1, studentId);
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