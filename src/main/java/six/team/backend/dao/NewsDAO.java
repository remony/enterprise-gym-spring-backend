package six.team.backend.dao;

import six.team.backend.store.NewsStore;
import six.team.backend.store.UserStore;

import java.sql.*;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Anna on 17/09/2015.
 */
public class NewsDAO {

    public void save(NewsStore newsStore) {

    }

    public void update(int newsId) {

    }
    public boolean delete(int newsId) {
        return true;
    }

    public NewsStore get(int userId) {
        NewsStore newsStore = new NewsStore();
        Connection connection = null;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select id, username from Users where id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //newsStore.setId(rs.getInt("id"));
               // newsStore.setUsername(rs.getString("username"));
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

        return newsStore;
    }


    public LinkedList<NewsStore> list(){
        LinkedList<NewsStore> allnews = new LinkedList<NewsStore>();
        Connection connection = null;

        try {
            connection = getDBConnection();

            PreparedStatement ps = connection.prepareStatement("select id, username from users");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NewsStore news = new NewsStore();
               // news.setId(rs.getInt("id"));
               // news.setUsername(rs.getString("username"));
                allnews.add(news);
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
        return allnews;
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


}
