/****************************************SC**********************************
 *****************
 * Name: Suresh Chitkara *
 * Course: Software Development I CEN-3024C-24667 *
 * Date: 4/13/2024 *
 * Description: The LibraryManagementSystem class acts as the backbone of the library management software,
 * facilitating the core functionalities and ensuring the efficient operation of the system as a whole.
 *****************************************SC**********************************
 ******************/

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The LibraryManagementSystem class serves as the backbone of the library management software,
 * facilitating core functionalities and ensuring the efficient operation of the system.
 *   @author Suresh Chitkara
 *   @version 1.0
 */
public class LibraryManagementSystem extends JFrame { // SC Connection to database.

    private Connection connection;
    private JTextArea bookListTextArea;

    /**
     * Constructs the GUI for the Library Management System.
     */
    public LibraryManagementSystem() { // SC  Initialize GUI components
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // SC Sets the default close operation to exit the application when the window is closed.

        bookListTextArea = new JTextArea(); // SC  Initializes a JScrollPane named scrollPane with bookListTextArea to provide scrolling functionality.
        JScrollPane scrollPane = new JScrollPane(bookListTextArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JButton removeButton = new JButton("Remove Book");
        JButton checkOutButton = new JButton("Check Out Book");
        JButton checkInButton = new JButton("Check In Book");

        removeButton.addActionListener(new ActionListener() { // SC Prompt the user to enter the title or barcode of the book to remove
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter the title or barcode of the book to remove:");
                if (input != null && !input.isEmpty()) { //SC Call the removeBook method with the user input as the parameter.
                    removeBook(input);
                    displayBookData();
                }
            }
        });

        checkOutButton.addActionListener(new ActionListener() { // SC Check if the input is not null and not empty
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter the title or barcode of the book to check out:");
                if (input != null && !input.isEmpty()) { // SC Call the checkOutBook method with the user input as the parameter.
                    checkOutBook(input);
                    displayBookData();
                }
            }
        });

        checkInButton.addActionListener(new ActionListener() { //  SC Prompt the user to enter the title or barcode of the book to check in
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter the title or barcode of the book to check in:");
                if (input != null && !input.isEmpty()) { // SC Call the checkInBook method with the user input as the parameter
                    checkInBook(input);
                    displayBookData();
                }
            }
        });
        // SC Layout components
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(removeButton);
        buttonPanel.add(checkOutButton);
        buttonPanel.add(checkInButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        // SC Connect to the database
        connectToDatabase();
        // SC Retrieve and display book data
        displayBookData();
    }

    /**
     * Connects to the database.
     */
    private void connectToDatabase() {
        try { // SC Load JDBC driver and establish connection
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "username", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the book inventory data.
     */
    private void displayBookData() { // SC Execute the SQL query to retrieve all books
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");
            StringBuilder bookList = new StringBuilder();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                int barcode = resultSet.getInt("barcode");
                String status = resultSet.getString("status");
                String dueDate = resultSet.getString("due_date");

                bookList.append(String.format("Title: %s, Author: %s, Genre: %s, Barcode: %d, Status: %s, Due Date: %s%n",
                        title, author, genre, barcode, status, dueDate));
            }
            bookListTextArea.setText(bookList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a book from the database based on the title or barcode provided.
     * @param input The title or barcode of the book to remove.
     */
    private void removeBook(String input) { // SC Prepare the SQL statement to delete a book by title or barcode
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE title = ? OR barcode = ?");
            statement.setString(1, input);
            try {
                int barcode = Integer.parseInt(input);
                statement.setInt(2, barcode);
            } catch (NumberFormatException e) {
                statement.setInt(2, -1); // SC Set a default value for barcode
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks out a book from the library based on the title or barcode provided.
     * @param input The title or barcode of the book to check out.
     */
    private void checkOutBook(String input) { // SC Set the due date to 7 days from the current date (assuming a 7-day checkout period)
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE books SET status = 'checked out', due_date = ? WHERE title = ? OR barcode = ?");
            statement.setDate(1, new java.sql.Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000))); // Assuming 7 days checkout period
            statement.setString(2, input);
            try {
                int barcode = Integer.parseInt(input);
                statement.setInt(3, barcode);
            } catch (NumberFormatException e) {
                statement.setInt(3, -1); // SC Set a default value for barcode
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks in a book to the library based on the title or barcode provided.
     * @param input The title or barcode of the book to check in.
     */
    private void checkInBook(String input) { // SC Prepare the SQL statement to update the status and due date of a book when checked in
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE books SET status = 'checked in', due_date = NULL WHERE title = ? OR barcode = ?");
            statement.setString(1, input);
            try {
                int barcode = Integer.parseInt(input);
                statement.setInt(2, barcode);
            } catch (NumberFormatException e) {
                statement.setInt(2, -1); // SC Set a default value for barcode
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method to initialize and display the GUI of the library management system.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LibraryManagementSystem().setVisible(true);
            }
        });
    }
}
