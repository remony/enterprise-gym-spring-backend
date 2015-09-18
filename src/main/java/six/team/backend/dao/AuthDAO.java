package six.team.backend.dao;

import java.sql.*;
import java.util.UUID;

/**
 * Created by Gareth on 18/09/2015.
 */
public class AuthDAO {

    public boolean verifyUser(String token, String username){
        Connection connection = null;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Users WHERE username = ? limit 1");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("username"));
                if(rs != null) {
                    String token1 = rs.getString("token");
                    if (token.equals(token1)) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                //failed to close connection
                System.err.println(e.getMessage());
            }
        }
        return false;
    }
    private static Connection getDBConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String db = "jdbc:mysql://46.101.32.73:3306/enterprisegym";
            connection = DriverManager.getConnection(db, "admin", "admin");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
