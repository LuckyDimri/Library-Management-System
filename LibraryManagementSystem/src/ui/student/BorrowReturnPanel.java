package ui.student;

import dao.BookDAO;
import dao.IssuedBookDAO;
import model.Book;
import model.IssuedBook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BorrowReturnPanel extends JPanel {
    private BookDAO bookDAO = new BookDAO();
    private IssuedBookDAO issuedBookDAO = new IssuedBookDAO();
    private int studentId;
    private DefaultTableModel tableModel;

    public BorrowReturnPanel(int studentId) {
        this.studentId = studentId;
        setLayout(new BorderLayout());

        // Table for available books
        String[] columns = {"ID", "Title", "Author", "ISBN", "Publisher", "Year", "Available"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Borrow button
        JButton borrowBtn = new JButton("Borrow Selected Book");
        add(borrowBtn, BorderLayout.SOUTH);

        borrowBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a book to borrow.");
                return;
            }
            int bookId = (int) tableModel.getValueAt(row, 0);

            // Set issue and due dates
            Date issueDate = new Date();
            Date dueDate = new Date(issueDate.getTime() + 14L * 24 * 60 * 60 * 1000); // 2 weeks

            IssuedBook ib = new IssuedBook();
            ib.setBookId(bookId);
            ib.setStudentId(studentId);
            ib.setIssueDate(issueDate);
            ib.setDueDate(dueDate);

            if (issuedBookDAO.issueBook(ib)) {
                JOptionPane.showMessageDialog(this, "Book borrowed! Due date: " + new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to borrow book.");
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Book> books = bookDAO.getAllBooks();
        for (Book b : books) {
            if ("AVAILABLE".equals(b.getStatus()) && b.getAvailableCopies() > 0) {
                tableModel.addRow(new Object[]{
                        b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(),
                        b.getPublisher(), b.getYear(), b.getAvailableCopies()
                });
            }
        }
    }
}