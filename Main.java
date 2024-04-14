/****************************************SC**********************************
 *****************
 * Name: Suresh Chitkara *
 * Course: Software Development I CEN-3024C-24667 *
 * Date: 4/6/2024 *
 * Description: The Main class displays the GUI for managing a library system, allowing users to add, delete, check in, and check out books.
 *****************************************SC**********************************
 ******************/

import javax.swing.SwingUtilities;

/**
 * The Main class serves as the entry point for the Library Management System application.
 * It initializes and displays the graphical user interface (GUI) for managing a library system.
 * Users can add books, delete books, and perform check in and check out operations.
 *
 * @author Suresh Chitkara
 * @version 1.0
 */
public class Main {

    /**
     * The main method, serving as the entry point for the application.
     * It creates an instance of LibraryManagementSystemGUI and makes the GUI visible.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) { // SC This is the main method declaration in Java.
        SwingUtilities.invokeLater(new Runnable() { // SC This line of code uses the invokeLater method from the SwingUtilities
            /**
             * The run method, executed asynchronously on the event dispatch thread (EDT) of the Swing application.
             */
            public void run() { // SC This line of code defines the run method inside an anonymous Runnable object.
                LibraryManagementSystemGUI gui = new LibraryManagementSystemGUI(); // SC This line of code creates a new instance of the LibraryManagementSystemGUI class.
                gui.setVisible(true); // SC  Making the GUI visible
            }
        });
    }
}
