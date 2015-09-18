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
        Connection connection = null;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("insert into News (title, text, date) values (?,?,?)");
            ps.setString(1, newsStore.getTitle());
            ps.setString(2, newsStore.getText());
            java.sql.Date sqlDate = new java.sql.Date(newsStore.getDate().getTime());
            ps.setDate(2, sqlDate);
             ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        }

    }

    public void update(int newsId,String title,String text) {

    }
    public void delete(int newsId) {
        Connection connection = null;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("delete from News where newsid=?");
            ps.setInt(1, newsId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public NewsStore get(int newsId) {
        NewsStore newsStore = new NewsStore();
        Connection connection = null;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select newsid, title, text, date from News where newsid = ?");
            ps.setInt(1, newsId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newsStore.setId(rs.getInt("newsid"));
                newsStore.setTitle(rs.getString("title"));
                newsStore.setText(rs.getString("title"));
               // newsStore.setDate(rs.getDate("date"));
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

            PreparedStatement ps = connection.prepareStatement("select newsid, title, text, date from news");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NewsStore news = new NewsStore();
                news.setId(rs.getInt("newsid"));
                news.setTitle(rs.getString("title"));
                news.setText(rs.getString("text"));
            //    news.setDate(rs.getString("date"));
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
