package ui.librarian;

import javax.swing.*;
import java.awt.*;

public class LibrarianDashboard extends JFrame {
    private JPanel mainPanel;

    public LibrarianDashboard() {
        setTitle("Librarian Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar for navigation
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(6, 1, 0, 10));
        sidebar.setBackground(new Color(60, 63, 65));
        sidebar.setPreferredSize(new Dimension(180, 0));

        JButton manageBooksBtn = new JButton("Manage Books");
        JButton issueReturnBtn = new JButton("Issue/Return Books");
        JButton issuedBooksBtn = new JButton("Issued/Overdue Books");
        JButton manageStudentsBtn = new JButton("Manage Students");
        JButton logoutBtn = new JButton("Logout");

        sidebar.add(manageBooksBtn);
        sidebar.add(issueReturnBtn);
        sidebar.add(issuedBooksBtn);
        sidebar.add(manageStudentsBtn);
        sidebar.add(new JLabel()); // Spacer
        sidebar.add(logoutBtn);

        add(sidebar, BorderLayout.WEST);

        // Main panel for feature panels
        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(new ManageBooksPanel(), "ManageBooks");
        mainPanel.add(new IssueReturnPanel(), "IssueReturn");
        mainPanel.add(new IssuedBooksPanel(), "IssuedBooks");
        mainPanel.add(new ManageStudentsPanel(), "ManageStudents");

        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        manageBooksBtn.addActionListener(e -> showPanel("ManageBooks"));
        issueReturnBtn.addActionListener(e -> showPanel("IssueReturn"));
        issuedBooksBtn.addActionListener(e -> showPanel("IssuedBooks"));
        manageStudentsBtn.addActionListener(e -> showPanel("ManageStudents"));
        logoutBtn.addActionListener(e -> {
            dispose();
            // Optionally, show login screen again
            // new ui.LoginFrame().setVisible(true);
        });

        showPanel("ManageBooks");
    }

    private void showPanel(String name) {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarianDashboard().setVisible(true));
    }
}