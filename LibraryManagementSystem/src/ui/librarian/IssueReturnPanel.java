package ui.librarian;

import dao.BookDAO;
import dao.IssuedBookDAO;
import model.Book;
import model.IssuedBook;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class IssueReturnPanel extends JPanel {
    private BookDAO bookDAO = new BookDAO();
    private IssuedBookDAO issuedBookDAO = new IssuedBookDAO();

    public IssueReturnPanel() {
        setLayout(new BorderLayout());

        // Issue Book Section
        JPanel issuePanel = new JPanel(new GridLayout(2, 4, 5, 5));
        JTextField studentIdField = new JTextField();
        JTextField bookIdField = new JTextField();
        JTextField dueDateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() + 14L * 24 * 60 * 60 * 1000))); // default 2 weeks
        JButton issueBtn = new JButton("Issue Book");

        issuePanel.setBorder(BorderFactory.createTitledBorder("Issue Book"));
        issuePanel.add(new JLabel("Student ID:"));
        issuePanel.add(studentIdField);
        issuePanel.add(new JLabel("Book ID:"));
        issuePanel.add(bookIdField);
        issuePanel.add(new JLabel("Due Date (yyyy-MM-dd):"));
        issuePanel.add(dueDateField);
        issuePanel.add(new JLabel());
        issuePanel.add(issueBtn);

        // Return Book Section
        JPanel returnPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        JTextField issuedBookIdField = new JTextField();
        JTextField returnDateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        JButton returnBtn = new JButton("Return Book");

        returnPanel.setBorder(BorderFactory.createTitledBorder("Return Book"));
        returnPanel.add(new JLabel("Issued Book ID:"));
        returnPanel.add(issuedBookIdField);
        returnPanel.add(new JLabel());
        returnPanel.add(new JLabel("Return Date (yyyy-MM-dd):"));
        returnPanel.add(returnDateField);
        returnPanel.add(returnBtn);

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(issuePanel);
        topPanel.add(returnPanel);

        add(topPanel, BorderLayout.NORTH);

        // Table to show issued books
        String[] columns = {"ID", "Book ID", "Student ID", "Issue Date", "Due Date", "Return Date", "Fine", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshTable(tableModel);

        // Issue Book Action
        issueBtn.addActionListener(e -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                int bookId = Integer.parseInt(bookIdField.getText());
                Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateField.getText());
                IssuedBook ib = new IssuedBook();
                ib.setStudentId(studentId);
                ib.setBookId(bookId);
                ib.setIssueDate(new Date());
                ib.setDueDate(dueDate);
                if (issuedBookDAO.issueBook(ib)) {
                    JOptionPane.showMessageDialog(this, "Book issued!");
                    refreshTable(tableModel);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to issue book.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input.");
            }
        });

        // Return Book Action
        returnBtn.addActionListener(e -> {
            try {
                int issuedBookId = Integer.parseInt(issuedBookIdField.getText());
                Date returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(returnDateField.getText());
                // Calculate fine (simple example: 10 per day overdue)
                IssuedBook ib = issuedBookDAO.getAllIssuedBooks().stream()
                        .filter(x -> x.getId() == issuedBookId)
                        .findFirst().orElse(null);
                if (ib == null) {
                    JOptionPane.showMessageDialog(this, "Issued book not found.");
                    return;
                }
                long overdueDays = (returnDate.getTime() - ib.getDueDate().getTime()) / (1000 * 60 * 60 * 24);
                double fine = overdueDays > 0 ? overdueDays * 10 : 0;
                if (issuedBookDAO.returnBook(issuedBookId, new java.sql.Date(returnDate.getTime()), fine)) {
                    JOptionPane.showMessageDialog(this, "Book returned! Fine: " + fine);
                    refreshTable(tableModel);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to return book.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input.");
            }
        });
    }

    private void refreshTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<IssuedBook> list = issuedBookDAO.getAllIssuedBooks();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (IssuedBook ib : list) {
            tableModel.addRow(new Object[]{
                    ib.getId(), ib.getBookId(), ib.getStudentId(),
                    ib.getIssueDate() != null ? sdf.format(ib.getIssueDate()) : "",
                    ib.getDueDate() != null ? sdf.format(ib.getDueDate()) : "",
                    ib.getReturnDate() != null ? sdf.format(ib.getReturnDate()) : "",
                    ib.getFine(), ib.getStatus()
            });
        }
    }
}