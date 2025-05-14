package ui.admin;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private JPanel mainPanel;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar for navigation
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(6, 1, 0, 10));
        sidebar.setBackground(new Color(60, 63, 65));
        sidebar.setPreferredSize(new Dimension(200, 0));

        JButton manageLibrariansBtn = new JButton("Manage Librarians");
        JButton fineReportsBtn = new JButton("Fine Reports");
        JButton systemReportsBtn = new JButton("System Reports");
        JButton manageUsersBtn = new JButton("Manage Users");
        JButton logoutBtn = new JButton("Logout");

        sidebar.add(manageLibrariansBtn);
        sidebar.add(fineReportsBtn);
        sidebar.add(systemReportsBtn);
        sidebar.add(manageUsersBtn);
        sidebar.add(new JLabel()); // Spacer
        sidebar.add(logoutBtn);

        add(sidebar, BorderLayout.WEST);

        // Main panel for feature panels
        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(new ManageLibrariansPanel(), "ManageLibrarians");
        mainPanel.add(new FineReportsPanel(), "FineReports");
        mainPanel.add(new SystemReportsPanel(), "SystemReports");
        mainPanel.add(new ManageUsersPanel(), "ManageUsers");

        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        manageLibrariansBtn.addActionListener(e -> showPanel("ManageLibrarians"));
        fineReportsBtn.addActionListener(e -> showPanel("FineReports"));
        systemReportsBtn.addActionListener(e -> showPanel("SystemReports"));
        manageUsersBtn.addActionListener(e -> showPanel("ManageUsers"));
        logoutBtn.addActionListener(e -> {
            dispose();
            // Optionally, show login screen again
            // new ui.LoginFrame().setVisible(true);
        });

        showPanel("ManageLibrarians");
    }

    private void showPanel(String name) {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, name);
    }

    // For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}