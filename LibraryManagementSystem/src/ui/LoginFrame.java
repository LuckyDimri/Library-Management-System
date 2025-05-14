package ui;

import dao.UserDAO;
import model.User;
import ui.admin.AdminDashboard;
import ui.librarian.LibrarianDashboard;
import ui.student.StudentDashboard;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Library Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(new JLabel()); // empty cell
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> login());

        // Enter key triggers login
        passwordField.addActionListener(e -> login());
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        UserDAO userDAO = new UserDAO();
        User user = userDAO.findByUsernameAndPassword(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.getRole());
            this.dispose(); // Close login window

            // Open the appropriate dashboard
            switch (user.getRole().toUpperCase()) {
                case "ADMIN":
                    SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
                    break;
                case "LIBRARIAN":
                    SwingUtilities.invokeLater(() -> new LibrarianDashboard().setVisible(true));
                    break;
                case "STUDENT":
                    // You may need to get the studentId from the user object or a StudentDAO
                    int studentId = user.getId(); // Adjust if your StudentDashboard needs a different ID
                    SwingUtilities.invokeLater(() -> new StudentDashboard(studentId).setVisible(true));
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Unknown user role.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}