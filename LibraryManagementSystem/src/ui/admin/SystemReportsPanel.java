package ui.admin;

import dao.BookDAO;
import dao.FineDAO;
import dao.LibrarianDAO;
import dao.StudentDAO;

import javax.swing.*;
import java.awt.*;

public class SystemReportsPanel extends JPanel {
    private BookDAO bookDAO = new BookDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private LibrarianDAO librarianDAO = new LibrarianDAO();
    private FineDAO fineDAO = new FineDAO();

    private JLabel totalBooksLabel;
    private JLabel totalStudentsLabel;
    private JLabel totalLibrariansLabel;
    private JLabel totalIssuedBooksLabel;
    private JLabel totalFinesLabel;

    public SystemReportsPanel() {
        setLayout(new GridLayout(6, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        totalBooksLabel = new JLabel();
        totalStudentsLabel = new JLabel();
        totalLibrariansLabel = new JLabel();
        totalIssuedBooksLabel = new JLabel();
        totalFinesLabel = new JLabel();

        add(new JLabel("Library System Overview", SwingConstants.CENTER));
        add(totalBooksLabel);
        add(totalStudentsLabel);
        add(totalLibrariansLabel);
        add(totalIssuedBooksLabel);
        add(totalFinesLabel);

        refreshStats();
    }

    private void refreshStats() {
        int totalBooks = bookDAO.getAllBooks().size();
        int totalStudents = studentDAO.getAllStudents().size();
        int totalLibrarians = librarianDAO.getAllLibrarians().size();
        int totalIssuedBooks = bookDAO.getTotalIssuedBooks();
        double totalFines = fineDAO.getTotalFinesCollected();

        totalBooksLabel.setText("Total Books: " + totalBooks);
        totalStudentsLabel.setText("Total Students: " + totalStudents);
        totalLibrariansLabel.setText("Total Librarians: " + totalLibrarians);
        totalIssuedBooksLabel.setText("Total Books Issued: " + totalIssuedBooks);
        totalFinesLabel.setText("Total Fines Collected: " + totalFines);
    }
}