package ui.librarian;

import dao.StudentDAO;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageStudentsPanel extends JPanel {
    private StudentDAO studentDAO = new StudentDAO();
    private DefaultTableModel tableModel;

    public ManageStudentsPanel() {
        setLayout(new BorderLayout());

        // Table
        String[] columns = {"ID", "User ID", "Name", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Add Student Form
        JPanel formPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JButton addBtn = new JButton("Add Student");

        formPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));
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

        // Delete Student Button
        JButton deleteBtn = new JButton("Delete Selected Student");
        add(deleteBtn, BorderLayout.SOUTH);

        // Add Student Action
        addBtn.addActionListener(e -> {
            Student s = new Student();
            s.setName(nameField.getText());
            s.setEmail(emailField.getText());
            s.setPhone(phoneField.getText());
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (studentDAO.addStudent(s, username, password)) {
                JOptionPane.showMessageDialog(this, "Student added!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add student.");
            }
        });

        // Delete Student Action
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a student to delete.");
                return;
            }
            int studentId = (int) tableModel.getValueAt(row, 0);
            int userId = (int) tableModel.getValueAt(row, 1);
            if (studentDAO.deleteStudent(studentId, userId)) {
                JOptionPane.showMessageDialog(this, "Student deleted!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete student.");
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Student> list = studentDAO.getAllStudents();
        for (Student s : list) {
            tableModel.addRow(new Object[]{
                    s.getId(), s.getUserId(), s.getName(), s.getEmail(), s.getPhone()
            });
        }
    }
}