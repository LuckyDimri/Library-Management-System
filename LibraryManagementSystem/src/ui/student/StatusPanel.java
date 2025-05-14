package ui.student;

import dao.IssuedBookDAO;
import model.IssuedBook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class StatusPanel extends JPanel {
    private IssuedBookDAO issuedBookDAO = new IssuedBookDAO();
    private int studentId;
    private DefaultTableModel tableModel;

    public StatusPanel(int studentId) {
        this.studentId = studentId;
        setLayout(new BorderLayout());

        String[] columns = {"Issued ID", "Book ID", "Issue Date", "Due Date", "Return Date", "Status", "Fine"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<IssuedBook> list = issuedBookDAO.getAllIssuedBooks();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (IssuedBook ib : list) {
            if (ib.getStudentId() == studentId) {
                tableModel.addRow(new Object[]{
                        ib.getId(), ib.getBookId(),
                        ib.getIssueDate() != null ? sdf.format(ib.getIssueDate()) : "",
                        ib.getDueDate() != null ? sdf.format(ib.getDueDate()) : "",
                        ib.getReturnDate() != null ? sdf.format(ib.getReturnDate()) : "",
                        ib.getStatus(),
                        ib.getFine()
                });
            }
        }
    }
}