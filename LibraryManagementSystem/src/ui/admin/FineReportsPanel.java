package ui.admin;

import dao.FineDAO;
import model.Fine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class FineReportsPanel extends JPanel {
    private FineDAO fineDAO = new FineDAO();
    private DefaultTableModel tableModel;

    public FineReportsPanel() {
        setLayout(new BorderLayout());

        // Table
        String[] columns = {"Fine ID", "Student ID", "Issued Book ID", "Amount", "Paid", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Filter panel
        JPanel filterPanel = new JPanel();
        JTextField studentIdField = new JTextField(8);
        JTextField monthField = new JTextField(8); // format: YYYY-MM
        JButton filterStudentBtn = new JButton("Filter by Student");
        JButton filterMonthBtn = new JButton("Filter by Month");
        JButton showAllBtn = new JButton("Show All");
        JLabel totalLabel = new JLabel("Total Fines Collected: 0.0");

        filterPanel.add(new JLabel("Student ID:"));
        filterPanel.add(studentIdField);
        filterPanel.add(filterStudentBtn);
        filterPanel.add(new JLabel("Month (YYYY-MM):"));
        filterPanel.add(monthField);
        filterPanel.add(filterMonthBtn);
        filterPanel.add(showAllBtn);
        filterPanel.add(totalLabel);

        add(filterPanel, BorderLayout.NORTH);

        // Button actions
        filterStudentBtn.addActionListener(e -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                refreshTable(fineDAO.getFinesByStudent(studentId));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid student ID.");
            }
        });

        filterMonthBtn.addActionListener(e -> {
            String month = monthField.getText().trim();
            if (!month.matches("\\d{4}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Month must be in YYYY-MM format.");
                return;
            }
            refreshTable(fineDAO.getFinesByMonth(month));
        });

        showAllBtn.addActionListener(e -> refreshTable(fineDAO.getAllFines()));

        // Show all fines by default
        refreshTable(fineDAO.getAllFines());

        // Show total fines collected
        totalLabel.setText("Total Fines Collected: " + fineDAO.getTotalFinesCollected());
    }

    private void refreshTable(List<Fine> fines) {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Fine f : fines) {
            tableModel.addRow(new Object[]{
                    f.getId(), f.getStudentId(), f.getIssuedBookId(),
                    f.getAmount(), f.isPaid() ? "Yes" : "No",
                    f.getDate() != null ? sdf.format(f.getDate()) : ""
            });
        }
    }
}