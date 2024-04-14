/****************************************SC**********************************
 *****************
 * Name: Suresh Chitkara *
 * Course: Software Development I CEN-3024C-24667 *
 * Date: 4/6/2024 *
 * Description: The LibraryManagementSystemGUI class serves as the user interface for interacting with the library management system, allowing users to perform essential operations related to managing the library's book inventory.
 *****************************************SC**********************************
 ******************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;

/**
 * The LibraryManagementSystemGUI class represents the graphical user interface (GUI) for managing a library system.
 * It allows users to perform essential operations such as adding, removing, checking out, and checking in books.
 *
 * @author Suresh Chitkara
 * @version 1.0
 */
public class LibraryManagementSystemGUI extends JFrame { // SC Class implementation

    private JTextField titleField; // SC Text field for entering books.
    private JTextField authorField;
    private JTextField genreField;
    private JTextField barcodeField;
    private JTextArea databaseTextArea;
    private JTextField barcodeToRemoveField;
    private JTextField titleToRemoveField;
    private JTextField titleToCheckoutField;
    private JTextField titleToCheckinField;
    private Connection connection; // SC This line of code declares a private instance variable named connection of type Connection.

    /**
     * Constructs a new LibraryManagementSystemGUI object.
     * Initializes the GUI components and connects to the database.
     */
    public LibraryManagementSystemGUI() { // SC  It initializes the GUI window by setting its title to "Library Management System"
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try { // SC Connect to MySQL database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/library", "username", "password");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(6, 2)); // SC This layout manager will arrange the components added to the panel in a grid, where each cell in the grid will have the same size.

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        authorField = new JTextField();
        JLabel genreLabel = new JLabel("Genre:");
        genreField = new JTextField();
        JLabel barcodeLabel = new JLabel("Barcode:");
        barcodeField = new JTextField();
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        // SC This method is expected to remove a book from the database based on the barcode provided by the user.
        JLabel barcodeToRemoveLabel = new JLabel("Barcode to Remove:");
        barcodeToRemoveField = new JTextField();
        JButton removeByBarcodeButton = new JButton("Remove by Barcode");
        removeByBarcodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBookByBarcode();
            }
        });
        // SC This method is expected to remove a book from the database based on the title provided by the user.
        JLabel titleToRemoveLabel = new JLabel("Title to Remove:");
        titleToRemoveField = new JTextField();
        JButton removeByTitleButton = new JButton("Remove by Title");
        removeByTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBookByTitle();
            }
        });
        // SC This method is expected to handle the checkout process for the book specified by the user.
        JLabel titleToCheckoutLabel = new JLabel("Title to Checkout:");
        titleToCheckoutField = new JTextField();
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkoutBook();
            }
        });
        // SC This method is expected to handle the check-in process for the book specified by the user.
        JLabel titleToCheckinLabel = new JLabel("Title to Checkin:");
        titleToCheckinField = new JTextField();
        JButton checkinButton = new JButton("Checkin");
        checkinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkinBook();
            }
        });
        // SC These components are organized in a grid layout within the topPanel, making it easy for the user to interact with them.
        topPanel.add(titleLabel);
        topPanel.add(titleField);
        topPanel.add(authorLabel);
        topPanel.add(authorField);
        topPanel.add(genreLabel);
        topPanel.add(genreField);
        topPanel.add(barcodeLabel);
        topPanel.add(barcodeField);
        topPanel.add(addButton);
        topPanel.add(new JLabel());
        topPanel.add(barcodeToRemoveLabel);
        topPanel.add(barcodeToRemoveField);
        topPanel.add(removeByBarcodeButton);
        topPanel.add(new JLabel());
        topPanel.add(titleToRemoveLabel);
        topPanel.add(titleToRemoveField);
        topPanel.add(removeByTitleButton);
        topPanel.add(new JLabel());
        topPanel.add(titleToCheckoutLabel);
        topPanel.add(titleToCheckoutField);
        topPanel.add(checkoutButton);
        topPanel.add(new JLabel());
        topPanel.add(titleToCheckinLabel);
        topPanel.add(titleToCheckinField);
        topPanel.add(checkinButton);

        add(topPanel, BorderLayout.NORTH);

        databaseTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(databaseTextArea);
        add(scrollPane, BorderLayout.CENTER);

        displayDatabase();
    }

    /**
     * Adds a book to the database using the information provided by the user through the GUI components.
     */
    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        int barcode = Integer.parseInt(barcodeField.getText());

        try { // SC This code demonstrates the use of parameterized queries to prevent SQL injection attacks and improve performance by allowing the database to reuse query execution plans.
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books (title, author, genre, barcode, status, due_date) VALUES (?, ?, ?, ?, 'Checked-in', NULL)");
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, genre);
            preparedStatement.setInt(4, barcode);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            displayDatabase();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Removes a book from the database based on the barcode provided by the user.
     */
    private void removeBookByBarcode() { // SC This code demonstrates the use of parameterized queries to prevent SQL injection attacks and improve performance by allowing the database to reuse query execution plans. It also handles potential exceptions that may occur during database operations.
        int barcodeToRemove = Integer.parseInt(barcodeToRemoveField.getText());
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM books WHERE barcode = ?");
            preparedStatement.setInt(1, barcodeToRemove);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                displayDatabase();
            } else {
                JOptionPane.showMessageDialog(this, "Book with barcode " + barcodeToRemove + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error removing book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Removes a book from the database based on the title provided by the user.
     */
    private void removeBookByTitle() { // SC method is responsible for removing a book from the database based on its title.
        String titleToRemove = titleToRemoveField.getText();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM books WHERE title = ?");
            preparedStatement.setString(1, titleToRemove);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                displayDatabase();
            } else {
                JOptionPane.showMessageDialog(this, "Book with title '" + titleToRemove + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error removing book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Checks out a book from the library by its title.
     */
    private void checkoutBook() { // SC method is responsible for checking out a book from the library by its title.
        String titleToCheckout = titleToCheckoutField.getText();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE title = ?");
            preparedStatement.setString(1, titleToCheckout);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String status = resultSet.getString("status");
                if (status.equals("Checked-in")) {
                    Calendar dueDate = Calendar.getInstance();
                    dueDate.add(Calendar.WEEK_OF_YEAR, 4);
                    Date dueDateObject = dueDate.getTime();
                    PreparedStatement updateStatement = connection.prepareStatement("UPDATE books SET status = 'Checked-out', due_date = ? WHERE title = ?");
                    updateStatement.setDate(1, new java.sql.Date(dueDateObject.getTime()));
                    updateStatement.setString(2, titleToCheckout);
                    updateStatement.executeUpdate();
                    updateStatement.close();
                    JOptionPane.showMessageDialog(this, "Book checked out successfully. Due date: " + dueDateObject.toString(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    displayDatabase();
                } else {
                    JOptionPane.showMessageDialog(this, "Book is already checked out.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Book with title '" + titleToCheckout + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error checking out book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Checks in a book to the library by its title.
     */
    private void checkinBook() { // SC method is responsible for checking in a book to the library by its title.
        String titleToCheckin = titleToCheckinField.getText();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE title = ?");
            preparedStatement.setString(1, titleToCheckin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String status = resultSet.getString("status");
                if (status.equals("Checked-out")) {
                    PreparedStatement updateStatement = connection.prepareStatement("UPDATE books SET status = 'Checked-in', due_date = NULL WHERE title = ?");
                    updateStatement.setString(1, titleToCheckin);
                    updateStatement.executeUpdate();
                    updateStatement.close();
                    JOptionPane.showMessageDialog(this, "Book checked in successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    displayDatabase();
                } else {
                    JOptionPane.showMessageDialog(this, "Book is not checked out.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Book with title '" + titleToCheckin + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error checking in book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays the book inventory from the database in the JTextArea.
     */
    private void displayDatabase() { // SC This method is typically called after an action that modifies the database (such as adding or removing a book) to refresh the displayed data and ensure it reflects the latest changes in the database.
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");
            StringBuilder stringBuilder = new StringBuilder();
            while (resultSet.next()) {
                stringBuilder.append("Title: ").append(resultSet.getString("title"))
                        .append(", Author: ").append(resultSet.getString("author"))
                        .append(", Genre: ").append(resultSet.getString("genre"))
                        .append(", Barcode: ").append(resultSet.getInt("barcode"))
                        .append(", Status: ").append(resultSet.getString("status"))
                        .append(", Due Date: ").append(resultSet.getString("due_date")).append("\n");
            }
            resultSet.close();
            statement.close();
            databaseTextArea.setText(stringBuilder.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Main method to initialize and display the GUI of the library management system.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) { // SC Main method initializes and displays the GUI of the library management system.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LibraryManagementSystemGUI gui = new LibraryManagementSystemGUI();
                gui.setVisible(true);
            }
        });
    }
}