package ui.student;

import dao.BookDAO;
import dao.BookRequestDAO;
import model.Book;
import model.BookRequest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class HoldPanel extends JPanel {
    private BookDAO bookDAO = new BookDAO();
    private BookRequestDAO bookRequestDAO = new BookRequestDAO();
    private int studentId;
    private DefaultTableModel tableModel;

    public HoldPanel(int studentId) {
        this.studentId = studentId;
        setLayout(new BorderLayout());

        // Table for all books
        String[] columns = {"Book ID", "Title", "Author", "Available"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Hold button
        JButton holdBtn = new JButton("Request Hold on Selected Book");
        add(holdBtn, BorderLayout.SOUTH);

        holdBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a book to hold.");
                return;
            }
            int bookId = (int) tableModel.getValueAt(row, 0);

            BookRequest request = new BookRequest();
            request.setStudentId(studentId);
            request.setBookId(bookId);
            request.setType("HOLD");
            request.setStatus("PENDING");
            request.setRequestDate(new Date());
            request.setComment("Requesting hold for 1 week.");

            if (bookRequestDAO.addBookRequest(request)) {
                JOptionPane.showMessageDialog(this, "Hold request submitted!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit hold request.");
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Book> books = bookDAO.getAllBooks();
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                    b.getId(), b.getTitle(), b.getAuthor(), b.getAvailableCopies()
            });
        }
    }
}