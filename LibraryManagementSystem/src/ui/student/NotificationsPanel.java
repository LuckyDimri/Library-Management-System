package ui.student;

import dao.NotificationDAO;
import model.Notification;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationsPanel extends JPanel {
    private NotificationDAO notificationDAO = new NotificationDAO();
    private int studentId;
    private DefaultTableModel tableModel;

    public NotificationsPanel(int studentId) {
        this.studentId = studentId;
        setLayout(new BorderLayout());

        String[] columns = {"Message", "Date", "Read"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton markReadBtn = new JButton("Mark All as Read");
        add(markReadBtn, BorderLayout.SOUTH);

        markReadBtn.addActionListener(e -> {
            notificationDAO.markAllAsRead(studentId);
            refreshTable();
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Notification> list = notificationDAO.getNotificationsForUser(studentId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Notification n : list) {
            tableModel.addRow(new Object[]{
                    n.getMessage(),
                    n.getCreatedAt() != null ? sdf.format(n.getCreatedAt()) : "",
                    n.isRead() ? "Yes" : "No"
            });
        }
    }
}