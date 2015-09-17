package six.team.backend.dao;


import six.team.backend.store.UserStore;


import java.security.SecureRandom;
import java.sql.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;


public class UserDAO {


    public void saveOrUpdate(UserStore userStore) {

    }


    public void delete(int userId) {

    }

    public UserStore get(int userId) {
        UserStore userStore = new UserStore();
        Connection connection = null;

        try {
            connection = getDBConnection();

            PreparedStatement ps = connection.prepareStatement("select id, username from Users where id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userStore.setId(rs.getInt("id"));
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

            PreparedStatement ps = connection.prepareStatement("select id, username from users");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserStore user = new UserStore();
                user.setId(rs.getInt("id"));
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


    private static Connection getDBConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String db = "jdbc:mysql://46.101.32.73:3306/enterprisegym";
            connection = DriverManager.getConnection(db,"admin","admin");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public String verifyUser(String username, String password){
        Connection connection = null;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT* FROM Users WHERE username=?");
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs.getString("username"));
            if(rs != null) {
                String passwordUser = rs.getString("password");
                if (password.equals(passwordUser)) {
                    String randomToken = UUID.randomUUID().toString();
                    randomToken = randomToken.replaceAll("-","");
                    PreparedStatement ps1 = connection.prepareStatement("UPDATE Users SET token=? WHERE username=?" );
                    ps1.setString(1, randomToken);
                    ps1.setString(2,username);
                    int rs1 = ps1.executeUpdate();
                    System.out.println(rs1);
                    return randomToken;
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
        return "LoginFailed";
    }
}
