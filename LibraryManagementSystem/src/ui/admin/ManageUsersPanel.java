package ui.admin;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageUsersPanel extends JPanel {
    private UserDAO userDAO = new UserDAO();
    private DefaultTableModel tableModel;

    public ManageUsersPanel() {
        setLayout(new BorderLayout());

        // Table
        String[] columns = {"User ID", "Username", "Role", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel();
        JButton activateBtn = new JButton("Activate");
        JButton deactivateBtn = new JButton("Deactivate");
        btnPanel.add(activateBtn);
        btnPanel.add(deactivateBtn);

        add(btnPanel, BorderLayout.SOUTH);

        // Activate user
        activateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a user to activate.");
                return;
            }
            int userId = (int) tableModel.getValueAt(row, 0);
            if (userDAO.updateStatus(userId, "ACTIVE")) {
                JOptionPane.showMessageDialog(this, "User activated!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to activate user.");
            }
        });

        // Deactivate user
        deactivateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a user to deactivate.");
                return;
            }
            int userId = (int) tableModel.getValueAt(row, 0);
            if (userDAO.updateStatus(userId, "INACTIVE")) {
                JOptionPane.showMessageDialog(this, "User deactivated!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to deactivate user.");
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<User> list = userDAO.getAllUsers();
        for (User u : list) {
            tableModel.addRow(new Object[]{
                    u.getId(), u.getUsername(), u.getRole(), u.getStatus()
            });
        }
    }
}