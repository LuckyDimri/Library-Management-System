package dao;

import model.IssuedBook;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IssuedBookDAO {

    // Issue a book
    public boolean issueBook(IssuedBook issuedBook) {
        String sql = "INSERT INTO issued_books (book_id, student_id, issue_date, due_date, status) VALUES (?, ?, ?, ?, 'ISSUED')";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, issuedBook.getBookId());
            stmt.setInt(2, issuedBook.getStudentId());
            stmt.setDate(3, new java.sql.Date(issuedBook.getIssueDate().getTime()));
            stmt.setDate(4, new java.sql.Date(issuedBook.getDueDate().getTime()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Return a book and calculate fine
    public boolean returnBook(int issuedBookId, Date returnDate, double fine) {
        String sql = "UPDATE issued_books SET return_date = ?, fine = ?, status = 'RETURNED' WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(returnDate.getTime()));
            stmt.setDouble(2, fine);
            stmt.setInt(3, issuedBookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all issued books
    public List<IssuedBook> getAllIssuedBooks() {
        List<IssuedBook> list = new ArrayList<>();
        String sql = "SELECT * FROM issued_books WHERE status = 'ISSUED'";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                IssuedBook ib = new IssuedBook();
                ib.setId(rs.getInt("id"));
                ib.setBookId(rs.getInt("book_id"));
                ib.setStudentId(rs.getInt("student_id"));
                ib.setIssueDate(rs.getDate("issue_date"));
                ib.setDueDate(rs.getDate("due_date"));
                ib.setReturnDate(rs.getDate("return_date"));
                ib.setFine(rs.getDouble("fine"));
                ib.setStatus(rs.getString("status"));
                list.add(ib);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // You can add more methods: getIssuedBooksByStudent(), getOverdueBooks(), etc.
}