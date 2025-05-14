package ui.student;

import dao.BookRequestDAO;
import model.BookRequest;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class RequestNewBookPanel extends JPanel {
    private BookRequestDAO bookRequestDAO = new BookRequestDAO();
    private int studentId;

    public RequestNewBookPanel(int studentId) {
        this.studentId = studentId;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField publisherField = new JTextField();
        JTextField yearField = new JTextField();
        JButton requestBtn = new JButton("Request Book");

        formPanel.setBorder(BorderFactory.createTitledBorder("Request a New Book"));
        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Author:"));
        formPanel.add(authorField);
        formPanel.add(new JLabel("Publisher:"));
        formPanel.add(publisherField);
        formPanel.add(new JLabel("Year:"));
        formPanel.add(yearField);
        formPanel.add(new JLabel());
        formPanel.add(requestBtn);

        add(formPanel, BorderLayout.NORTH);

        requestBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String publisher = publisherField.getText().trim();
            String year = yearField.getText().trim();

            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title is required.");
                return;
            }

            BookRequest request = new BookRequest();
            request.setStudentId(studentId);
            request.setType("NEW");
            request.setStatus("PENDING");
            request.setRequestDate(new Date());
            // For a new book request, you can store details in a comment or extend the BookRequest model
            request.setComment("Title: " + title + ", Author: " + author + ", Publisher: " + publisher + ", Year: " + year);

            if (bookRequestDAO.addBookRequest(request)) {
                JOptionPane.showMessageDialog(this, "Book request submitted!");
                titleField.setText("");
                authorField.setText("");
                publisherField.setText("");
                yearField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit request.");
            }
        });
    }
}