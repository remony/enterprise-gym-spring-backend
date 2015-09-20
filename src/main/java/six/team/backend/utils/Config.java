package six.team.backend.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
    /*
            class: Config
            Description: This class is used for configuration methods which will be reused in multiple classes
     */
public class Config {

    public static Connection getDBConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String path = "jdbc:mysql://46.101.32.73:3306/enterprisegym"; // The url or path to the database
            connection = DriverManager.getConnection(path, "admin", "admin"); // Getting the connection and providing url and user details to connect to the database
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection; // Return the database connection
    }
}
