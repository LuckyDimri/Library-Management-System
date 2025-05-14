package ui.student;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {
    private JPanel mainPanel;

    public StudentDashboard(int studentId) {
        setTitle("Student Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar for navigation
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(8, 1, 0, 10));
        sidebar.setBackground(new Color(60, 63, 65));
        sidebar.setPreferredSize(new Dimension(180, 0));

        JButton borrowBtn = new JButton("Borrow Books");
        JButton returnBtn = new JButton("Return Books");
        JButton statusBtn = new JButton("Borrowing Status");
        JButton requestNewBtn = new JButton("Request New Book");
        JButton holdBtn = new JButton("Request Hold");
        JButton reissueBtn = new JButton("Reissue Book");
        JButton notificationsBtn = new JButton("Notifications");
        JButton logoutBtn = new JButton("Logout");

        sidebar.add(borrowBtn);
        sidebar.add(returnBtn);
        sidebar.add(statusBtn);
        sidebar.add(requestNewBtn);
        sidebar.add(holdBtn);
        sidebar.add(reissueBtn);
        sidebar.add(notificationsBtn);
        sidebar.add(logoutBtn);

        add(sidebar, BorderLayout.WEST);

        // Main panel for feature panels
        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(new BorrowReturnPanel(studentId), "BorrowBooks");
        mainPanel.add(new ReturnPanel(studentId), "ReturnBooks");
        mainPanel.add(new StatusPanel(studentId), "Status");
        mainPanel.add(new RequestNewBookPanel(studentId), "RequestNew");
        mainPanel.add(new HoldPanel(studentId), "Hold");
        mainPanel.add(new ReissuePanel(studentId), "Reissue");
        mainPanel.add(new NotificationsPanel(studentId), "Notifications");

        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        borrowBtn.addActionListener(e -> showPanel("BorrowBooks"));
        returnBtn.addActionListener(e -> showPanel("ReturnBooks"));
        statusBtn.addActionListener(e -> showPanel("Status"));
        requestNewBtn.addActionListener(e -> showPanel("RequestNew"));
        holdBtn.addActionListener(e -> showPanel("Hold"));
        reissueBtn.addActionListener(e -> showPanel("Reissue"));
        notificationsBtn.addActionListener(e -> showPanel("Notifications"));
        logoutBtn.addActionListener(e -> {
            dispose();
            // Optionally, show login screen again
            // new ui.LoginFrame().setVisible(true);
        });

        showPanel("BorrowBooks");
    }

    private void showPanel(String name) {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, name);
    }

    // For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDashboard(1).setVisible(true));
    }
}