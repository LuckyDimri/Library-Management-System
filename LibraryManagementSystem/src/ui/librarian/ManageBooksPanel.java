package ui.librarian;

import dao.BookDAO;
import model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageBooksPanel extends JPanel {
    private BookDAO bookDAO = new BookDAO();
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public ManageBooksPanel() {
        setLayout(new BorderLayout());

        // Table setup
        String[] columns = {"ID", "Title", "Author", "ISBN", "Publisher", "Year", "Status", "Total", "Available"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        refreshTable();

        // Add Book Form
        JPanel formPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField isbnField = new JTextField();
        JTextField publisherField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField copiesField = new JTextField();
        JButton addButton = new JButton("Add Book");

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Author:"));
        formPanel.add(authorField);
        formPanel.add(new JLabel("ISBN:"));
        formPanel.add(isbnField);
        formPanel.add(new JLabel("Publisher:"));
        formPanel.add(publisherField);
        formPanel.add(new JLabel("Year:"));
        formPanel.add(yearField);
        formPanel.add(new JLabel("Copies:"));
        formPanel.add(copiesField);
        formPanel.add(addButton);

        add(new JScrollPane(bookTable), BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            Book book = new Book();
            book.setTitle(titleField.getText());
            book.setAuthor(authorField.getText());
            book.setIsbn(isbnField.getText());
            book.setPublisher(publisherField.getText());
            book.setYear(Integer.parseInt(yearField.getText()));
            book.setStatus("AVAILABLE");
            int copies = Integer.parseInt(copiesField.getText());
            book.setTotalCopies(copies);
            book.setAvailableCopies(copies);

            if (bookDAO.addBook(book)) {
                JOptionPane.showMessageDialog(this, "Book added!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add book.");
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Book> books = bookDAO.getAllBooks();
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(),
                b.getPublisher(), b.getYear(), b.getStatus(),
                b.getTotalCopies(), b.getAvailableCopies()
            });
        }
    }
}