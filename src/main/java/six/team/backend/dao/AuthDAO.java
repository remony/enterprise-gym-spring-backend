package six.team.backend.dao;

import six.team.backend.utils.Config;

import java.sql.*;
import java.util.UUID;

/**
 * Created by Gareth on 18/09/2015.
 */
public class AuthDAO {

    public boolean verifyUser(String token, String username){
        Connection connection = null;
        boolean isAuthorised = false;

        try {
            connection = Config.getDBConnection(); // creates connection to the database
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Users WHERE username = ? limit 1");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { //checks if the token for the username matches the token given
                if(rs != null) {
                    String token1 = rs.getString("token");
                    if (token.equals(token1)) {
                        isAuthorised = true;
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
        return isAuthorised;
    }
}
