package dao;

import model.Fine;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FineDAO {

    // Get all fines
    public List<Fine> getAllFines() {
        List<Fine> list = new ArrayList<>();
        String sql = "SELECT * FROM fines";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Fine f = new Fine();
                f.setId(rs.getInt("id"));
                f.setStudentId(rs.getInt("student_id"));
                f.setIssuedBookId(rs.getInt("issued_book_id"));
                f.setAmount(rs.getDouble("amount"));
                f.setPaid(rs.getBoolean("paid"));
                f.setDate(rs.getDate("date"));
                list.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get fines by student
    public List<Fine> getFinesByStudent(int studentId) {
        List<Fine> list = new ArrayList<>();
        String sql = "SELECT * FROM fines WHERE student_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Fine f = new Fine();
                f.setId(rs.getInt("id"));
                f.setStudentId(rs.getInt("student_id"));
                f.setIssuedBookId(rs.getInt("issued_book_id"));
                f.setAmount(rs.getDouble("amount"));
                f.setPaid(rs.getBoolean("paid"));
                f.setDate(rs.getDate("date"));
                list.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get fines by month (format: "YYYY-MM")
    public List<Fine> getFinesByMonth(String yearMonth) {
        List<Fine> list = new ArrayList<>();
        String sql = "SELECT * FROM fines WHERE DATE_FORMAT(date, '%Y-%m') = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, yearMonth);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Fine f = new Fine();
                f.setId(rs.getInt("id"));
                f.setStudentId(rs.getInt("student_id"));
                f.setIssuedBookId(rs.getInt("issued_book_id"));
                f.setAmount(rs.getDouble("amount"));
                f.setPaid(rs.getBoolean("paid"));
                f.setDate(rs.getDate("date"));
                list.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get total fines collected
    public double getTotalFinesCollected() {
        String sql = "SELECT SUM(amount) FROM fines WHERE paid = TRUE";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}