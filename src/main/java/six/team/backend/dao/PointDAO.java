package six.team.backend.dao;

import six.team.backend.store.UserStore;
import six.team.backend.utils.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Gareth on 23/09/2015.
 */
public class PointDAO {
    public boolean updatePoints(int userid, int points, String category)
    {
        Connection connection;
        connection = Config.getDBConnection();
        boolean success=false;
        try {
            PreparedStatement ps = connection.prepareStatement("Select * from Points where user_id =?");
            ps.setInt(1,userid);
            ResultSet rs = ps.executeQuery();
            if(rs==null){
                PreparedStatement ps1 = connection.prepareStatement("Insert Into Points (user_id) values(?)");
                ps1.setInt(1,userid);
                ps1.executeQuery();
            }
            PreparedStatement ps2 = connection.prepareStatement("UPDATE Points set " + category + " = " + category + "+ ? where user_id=?");
            ps2.setInt(1, points);
            ps2.setInt(2,userid);
            ps2.executeUpdate();
            success=true;
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
            success= false;
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                //failed to close connection
                System.err.println(e.getMessage());
            }
        }
        return success;
    }
}
