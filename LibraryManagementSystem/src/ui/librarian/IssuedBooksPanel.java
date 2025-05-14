package ui.librarian;

import dao.IssuedBookDAO;
import model.IssuedBook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class IssuedBooksPanel extends JPanel {
    private IssuedBookDAO issuedBookDAO = new IssuedBookDAO();
    private DefaultTableModel tableModel;

    public IssuedBooksPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Book ID", "Student ID", "Issue Date", "Due Date", "Status", "Fine", "Overdue"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        JTable table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new OverdueCellRenderer());

        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton notifyBtn = new JButton("Notify Overdue Students");
        add(notifyBtn, BorderLayout.SOUTH);

        notifyBtn.addActionListener(e -> {
            int notified = notifyOverdueStudents();
            JOptionPane.showMessageDialog(this, "Notified " + notified + " overdue students.");
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<IssuedBook> list = issuedBookDAO.getAllIssuedBooks();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        for (IssuedBook ib : list) {
            boolean isOverdue = ib.getDueDate() != null && ib.getDueDate().before(today);
            tableModel.addRow(new Object[]{
                    ib.getId(), ib.getBookId(), ib.getStudentId(),
                    ib.getIssueDate() != null ? sdf.format(ib.getIssueDate()) : "",
                    ib.getDueDate() != null ? sdf.format(ib.getDueDate()) : "",
                    ib.getStatus(),
                    ib.getFine(),
                    isOverdue ? "YES" : ""
            });
        }
    }

    // Dummy notification logic (replace with real notification system)
    private int notifyOverdueStudents() {
        int count = 0;
        Date today = new Date();
        List<IssuedBook> list = issuedBookDAO.getAllIssuedBooks();
        for (IssuedBook ib : list) {
            if (ib.getDueDate() != null && ib.getDueDate().before(today)) {
                // Here you would insert a notification into the notifications table
                // For now, just count
                count++;
            }
        }
        return count;
    }

    // Custom cell renderer to highlight overdue rows
    private class OverdueCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                      boolean isSelected, boolean hasFocus,
                                                      int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String overdue = (String) table.getModel().getValueAt(row, 7);
            if ("YES".equals(overdue)) {
                c.setBackground(new Color(255, 204, 204)); // Light red
            } else {
                c.setBackground(Color.WHITE);
            }
            return c;
        }
    }
}