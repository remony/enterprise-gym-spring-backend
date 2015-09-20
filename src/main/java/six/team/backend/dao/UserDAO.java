package six.team.backend.dao;


import six.team.backend.model.User;
import six.team.backend.store.UserLoginStore;
import six.team.backend.store.UserStore;


import java.security.SecureRandom;
import java.sql.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.UUID;


public class UserDAO {


    public void saveOrUpdate(UserStore userStore) {

    }



    public UserStore get(int userId) {
        UserStore userStore = new UserStore();
        Connection connection = null;

        try {
            connection = getDBConnection();

            PreparedStatement ps = connection.prepareStatement("select userid, username from Users where id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userStore.setId(rs.getInt("userid"));
                userStore.setUsername(rs.getString("username"));
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

        return userStore;
    }


    public LinkedList<UserStore> list(){
        LinkedList<UserStore> users = new LinkedList<UserStore>();
        Connection connection = null;

        try {
            connection = getDBConnection();

            PreparedStatement ps = connection.prepareStatement("select userid, username from users");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserStore user = new UserStore();
                user.setId(rs.getInt("userid"));
                user.setUsername(rs.getString("username"));
                users.add(user);
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
        return users;
    }

    public LinkedList<UserStore> unauthorisedList(){
        LinkedList<UserStore> users = new LinkedList<UserStore>();
        Connection connection = null;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select userid, username, usergroup from Users where usergroup = ?");
            ps.setString(1, "unauthorised");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserStore user = new UserStore();
                user.setId(rs.getInt("userid"));
                user.setUsername(rs.getString("username"));
                //user.setUsergroup(rs.getString("usergroup"));
                users.add(user);
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
        return users;
    }


    private static Connection getDBConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String db = "jdbc:mysql://46.101.32.73:3306/enterprisegym";
            connection = DriverManager.getConnection(db,"admin", "admin");


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public UserLoginStore verifyUser(String username, String password){
        Connection connection = null;
        UserLoginStore user = new UserLoginStore();
        user.setMessage("LoginFailed");
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT* FROM Users WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if (rs != null) {
                    String passwordUser = rs.getString("password");
                    if (password.equals(passwordUser)) {
                        String randomToken = UUID.randomUUID().toString();
                        randomToken = randomToken.replaceAll("-", "");
                        PreparedStatement ps1 = connection.prepareStatement("UPDATE Users SET token=? WHERE username=?");
                        ps1.setString(1, randomToken);
                        ps1.setString(2, username);
                        int rs1 = ps1.executeUpdate();
                        user.setToken(randomToken);
                        user.setUsergroup(rs.getString("usergroup"));
                        user.setMessage("Login Success");
                        if(rs.getString("usergroup").equals("unauthorised")){
                            user.setMessage("Unapproved");
                        }
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
        return user;
    }

    public boolean approveUser(int user_id,String user_group){
        Connection connection;
        connection = getDBConnection();

        try {

                PreparedStatement ps = connection.prepareStatement("update Users set usergroup=? where userid = ?");
                ps.setString(1, user_group);
                ps.setInt(2, user_id);
                ps.executeUpdate();
                return true;
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
            return false;
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

    }


    public boolean deleteUser(int user_id)
    {
        Connection connection;
        connection = getDBConnection();
        try {

                PreparedStatement ps = connection.prepareStatement("delete from Users where userid = ?");
                ps.setInt(1, user_id);
                ps.executeUpdate();
            return true;

        }
        catch(SQLException e){
            System.out.print(e.getMessage());
            return false;
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

    }
}
