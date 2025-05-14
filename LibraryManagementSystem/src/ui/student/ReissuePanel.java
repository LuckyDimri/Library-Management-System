package ui.student;

import dao.IssuedBookDAO;
import dao.BookRequestDAO;
import model.IssuedBook;
import model.BookRequest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class ReissuePanel extends JPanel {
    private IssuedBookDAO issuedBookDAO = new IssuedBookDAO();
    private BookRequestDAO bookRequestDAO = new BookRequestDAO();
    private int studentId;
    private DefaultTableModel tableModel;

    public ReissuePanel(int studentId) {
        this.studentId = studentId;
        setLayout(new BorderLayout());

        // Table for currently issued books
        String[] columns = {"Issued ID", "Book ID", "Due Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Reissue button
        JButton reissueBtn = new JButton("Request Reissue for Selected Book");
        add(reissueBtn, BorderLayout.SOUTH);

        reissueBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a book to reissue.");
                return;
            }
            int issuedBookId = (int) tableModel.getValueAt(row, 0);
            int bookId = (int) tableModel.getValueAt(row, 1);

            BookRequest request = new BookRequest();
            request.setStudentId(studentId);
            request.setBookId(bookId);
            request.setType("REISSUE");
            request.setStatus("PENDING");
            request.setRequestDate(new Date());
            request.setComment("Requesting reissue for issued book ID: " + issuedBookId);

            if (bookRequestDAO.addBookRequest(request)) {
                JOptionPane.showMessageDialog(this, "Reissue request submitted!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit reissue request.");
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<IssuedBook> list = issuedBookDAO.getAllIssuedBooks();
        for (IssuedBook ib : list) {
            if (ib.getStudentId() == studentId && "ISSUED".equals(ib.getStatus())) {
                tableModel.addRow(new Object[]{
                        ib.getId(), ib.getBookId(), ib.getDueDate(), ib.getStatus()
                });
            }
        }
    }
}