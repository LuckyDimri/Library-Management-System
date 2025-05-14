package ui.admin;

import dao.LibrarianDAO;
import model.Librarian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageLibrariansPanel extends JPanel {
    private LibrarianDAO librarianDAO = new LibrarianDAO();
    private DefaultTableModel tableModel;

    public ManageLibrariansPanel() {
        setLayout(new BorderLayout());

        // Table
        String[] columns = {"ID", "User ID", "Name", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Add Librarian Form
        JPanel formPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JButton addBtn = new JButton("Add Librarian");

        formPanel.setBorder(BorderFactory.createTitledBorder("Add Librarian"));
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(addBtn);

        add(formPanel, BorderLayout.NORTH);

        // Delete Librarian Button
        JButton deleteBtn = new JButton("Delete Selected Librarian");
        add(deleteBtn, BorderLayout.SOUTH);

        // Add Librarian Action
        addBtn.addActionListener(e -> {
            Librarian l = new Librarian();
            l.setName(nameField.getText());
            l.setEmail(emailField.getText());
            l.setPhone(phoneField.getText());
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (librarianDAO.addLibrarian(l, username, password)) {
                JOptionPane.showMessageDialog(this, "Librarian added!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add librarian.");
            }
        });

        // Delete Librarian Action
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a librarian to delete.");
                return;
            }
            int librarianId = (int) tableModel.getValueAt(row, 0);
            int userId = (int) tableModel.getValueAt(row, 1);
            if (librarianDAO.deleteLibrarian(librarianId, userId)) {
                JOptionPane.showMessageDialog(this, "Librarian deleted!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete librarian.");
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Librarian> list = librarianDAO.getAllLibrarians();
        for (Librarian l : list) {
            tableModel.addRow(new Object[]{
                    l.getId(), l.getUserId(), l.getName(), l.getEmail(), l.getPhone()
            });
        }
    }
}