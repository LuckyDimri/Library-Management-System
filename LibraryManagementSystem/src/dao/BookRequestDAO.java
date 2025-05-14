package dao;

import model.BookRequest;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class BookRequestDAO {
    public boolean addBookRequest(BookRequest request) {
        String sql = "INSERT INTO book_requests (student_id, book_id, type, status, request_date, comment) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, request.getStudentId());
            if (request.getBookId() != null) {
                stmt.setInt(2, request.getBookId());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setString(3, request.getType());
            stmt.setString(4, request.getStatus());
            stmt.setDate(5, new Date(request.getRequestDate().getTime()));
            stmt.setString(6, request.getComment());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}