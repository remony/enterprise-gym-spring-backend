package six.team.backend.dao;

import six.team.backend.store.ParticipantStore;
import six.team.backend.store.PointStore;
import six.team.backend.store.UserStore;
import six.team.backend.utils.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

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
            if(!rs.next()){
                    PreparedStatement ps1 = connection.prepareStatement("Insert Into Points (user_id) values(?)");
                    ps1.setInt(1, userid);
                    ps1.executeUpdate();
            }
            PreparedStatement ps2 = connection.prepareStatement("UPDATE Points set " + category + " = " + category + "+ ?, total = total + ? where user_id=?");
            ps2.setInt(1, points);
            ps2.setInt(2,points);
            ps2.setInt(3,userid);
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

    public LinkedList<PointStore> allPoints(){
        LinkedList<PointStore> points = new LinkedList<PointStore>();
        Connection connection = null;
        try {
            connection = Config.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select* from Points ORDER BY total DESC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                    PointStore pointStore = new PointStore();
                    pointStore.setUserid(rs.getInt("user_id"));
                    pointStore.setEnterprise_challenge(rs.getInt("enterprise_challenge"));
                    pointStore.setAction(rs.getInt("action"));
                    pointStore.setProject(rs.getInt("project"));
                    pointStore.setTheory(rs.getInt("theory"));
                    pointStore.setVirtual(rs.getInt("virtual"));
                    pointStore.setTotal(rs.getInt("total"));
                    pointStore.setUser(getUserDetails(rs.getInt("user_id")));
                    points.add(pointStore);
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
        return points;
    }

    private LinkedList<UserStore> getUserDetails(int user_id) {
        Connection connection;
        LinkedList<UserStore> user = new LinkedList<UserStore>();
        connection = Config.getDBConnection();
        //boolean success=false;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from Users where userid =?");
            ps.setInt(1, user_id);
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserStore news = new UserStore();
                news.setId(rs.getInt("userid"));
                news.setUsername(rs.getString("username"));
                news.setFirstname(rs.getString("firstname"));
                news.setLastname(rs.getString("lastname"));
                news.setContactnumber(rs.getString("contactnumber"));
                news.setEmail(rs.getString("email"));
                news.setCountry(rs.getString("country"));
                news.setUniversity(rs.getString("university"));
                news.setStatus(rs.getString("status"));
                news.setYearofstudy(rs.getInt("yearofstudy"));
                news.setUsergroup(rs.getString("usergroup"));
                user.add(news);
            }
        }
        catch(SQLException e){
            System.out.print(e.getMessage());

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
        return user;
    }

    public boolean resetPoints()
    {
        Connection connection;
        connection = Config.getDBConnection();
        boolean success=false;
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Points set enterprise_challenge=0,theory =0, project=0,action=0,virtual =0, total =0");
            ps.executeUpdate();
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

    public LinkedList<PointStore> getUserPoints(String username)
    {
        Connection connection;
        connection = Config.getDBConnection();
        PointStore userPoints = new PointStore();
        LinkedList<PointStore> points = new LinkedList<PointStore>();
        try {
            PreparedStatement ps1 = connection.prepareStatement("Select * FROM Users WHERE username =? limit 1");
            ps1.setString(1,username);
            ResultSet rs1 = ps1.executeQuery();
            int userid =0;
            while(rs1.next()) {
               userid = rs1.getInt("userid");
            }
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Points WHERE user_id =?");
            ps.setInt(1,userid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                userPoints.setUserid(rs.getInt("user_id"));
                userPoints.setEnterprise_challenge(rs.getInt("enterprise_challenge"));
                userPoints.setAction(rs.getInt("action"));
                userPoints.setProject(rs.getInt("project"));
                userPoints.setTheory(rs.getInt("theory"));
                userPoints.setVirtual(rs.getInt("virtual"));
                userPoints.setTotal(rs.getInt("total"));
                points.add(userPoints);
            }
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
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
        return points;
    }
}
