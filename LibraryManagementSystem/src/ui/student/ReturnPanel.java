package ui.student;

import dao.IssuedBookDAO;
import model.IssuedBook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReturnPanel extends JPanel {
    private IssuedBookDAO issuedBookDAO = new IssuedBookDAO();
    private int studentId;
    private DefaultTableModel tableModel;

    public ReturnPanel(int studentId) {
        this.studentId = studentId;
        setLayout(new BorderLayout());

        // Table for borrowed books
        String[] columns = {"Issued ID", "Book ID", "Issue Date", "Due Date", "Status", "Fine"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Return button
        JButton returnBtn = new JButton("Return Selected Book");
        add(returnBtn, BorderLayout.SOUTH);

        returnBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a book to return.");
                return;
            }
            int issuedBookId = (int) tableModel.getValueAt(row, 0);
            Date dueDate;
            try {
                dueDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) tableModel.getValueAt(row, 3));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error parsing due date.");
                return;
            }
            Date returnDate = new Date();
            long overdueDays = (returnDate.getTime() - dueDate.getTime()) / (1000 * 60 * 60 * 24);
            double fine = overdueDays > 0 ? overdueDays * 10 : 0;

            if (issuedBookDAO.returnBook(issuedBookId, new java.sql.Date(returnDate.getTime()), fine)){
                JOptionPane.showMessageDialog(this, "Book returned! Fine: " + fine);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to return book.");
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<IssuedBook> list = issuedBookDAO.getAllIssuedBooks();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (IssuedBook ib : list) {
            if (ib.getStudentId() == studentId && "ISSUED".equals(ib.getStatus())) {
                tableModel.addRow(new Object[]{
                        ib.getId(), ib.getBookId(),
                        ib.getIssueDate() != null ? sdf.format(ib.getIssueDate()) : "",
                        ib.getDueDate() != null ? sdf.format(ib.getDueDate()) : "",
                        ib.getStatus(),
                        ib.getFine()
                });
            }
        }
    }
}