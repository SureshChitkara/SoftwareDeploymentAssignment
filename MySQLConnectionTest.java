/****************************************SC**********************************
 *****************
 * Name: Suresh Chitkara *
 * Course: Software Development I CEN-3024C-24667 *
 * Date: 4/13/2024 *
 * Description: MySQLConnectionTest checks the connectivity to a MySQL database and verifies if the necessary configurations for establishing a connection are correctly set up.
 *****************************************SC**********************************
 ******************/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The MySQLConnectionTest class checks the connectivity to a MySQL database and verifies if the necessary configurations for establishing a connection are correctly set up.
 * @author Suresh Chitkara
 * @version 1.0
 */
public class MySQLConnectionTest {

    /**
     * The main method checks the connectivity to the MySQL database.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3307/library"; // SC URL for the database.
        String username = "root"; // SC Username for the localhost.
        String password = "Incorrect7#"; // SC Password for the localhost.

        try {
            Connection conn = DriverManager.getConnection(url, username, password); // SC Establish a connection to the MySQL database.
            if (conn != null) { // SC If an SQLException occurs during the connection process.
                System.out.println("Connected to the MySQL database.");
                conn.close(); // SC Close the connection.
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the MySQL database: " + e.getMessage());
        }
    }
}
