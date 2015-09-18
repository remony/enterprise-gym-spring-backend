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

    public boolean save(NewsStore newsStore) {
        Connection connection = null;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("insert into News (title,slug, text, datecreated,lastupdated,permission) values (?,?,?,?,?,?)");
            java.sql.Date sqlDate = new java.sql.Date(newsStore.getDateCreated().getTime());
            ps.setString(1, newsStore.getTitle());
            ps.setString(2, newsStore.getSlug());
            ps.setString(3, newsStore.getText());
            ps.setDate(4, sqlDate);
            ps.setDate(5, sqlDate);
            ps.setString(6, newsStore.getPermission());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return true;

    }

    public boolean update(NewsStore newsStore) {
        Connection connection = null;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("update News set title=?,slug=?, text=?, lastupdated=?,permission=? where newsid=?");
            ps.setString(1, newsStore.getTitle());
            ps.setString(2, newsStore.getSlug());
            ps.setString(3, newsStore.getText());
            java.sql.Date sqlDate = new java.sql.Date(newsStore.getLastedited().getTime());
            ps.setDate(4, sqlDate);
            ps.setString(5, newsStore.getPermission());
            ps.setInt(6, newsStore.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return true;
    }

    public boolean delete(int newsId) {
        Connection connection = null;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("delete from News where newsid=?");
            ps.setInt(1, newsId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    public NewsStore get(int newsId) {
        NewsStore newsStore = new NewsStore();
        Connection connection = null;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select newsid, title,text,permission,slug,lastupdated,datecreated from News where newsid=?");
            ps.setInt(1, newsId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newsStore.setId(rs.getInt("newsid"));
                newsStore.setTitle(rs.getString("title"));
                newsStore.setText(rs.getString("text"));
                newsStore.setPermission(rs.getString("permission"));
                newsStore.setSlug(rs.getString("slug"));
                newsStore.setLastedited(rs.getDate("lastupdated"));
                newsStore.setDateCreated(rs.getDate("datecreated"));
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

            PreparedStatement ps = connection.prepareStatement("select newsid, title,text,permission,slug,lastupdated,datecreated from News");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NewsStore news = new NewsStore();
                news.setId(rs.getInt("newsid"));
                news.setTitle(rs.getString("title"));
                news.setText(rs.getString("text"));
                news.setPermission(rs.getString("permission"));
                news.setSlug(rs.getString("slug"));
                news.setLastedited(rs.getDate("lastupdated"));
                news.setDateCreated(rs.getDate("datecreated"));
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
