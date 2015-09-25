package six.team.backend.dao;

import six.team.backend.store.IndexStore;

import java.sql.*;

/**
 * Created by Gareth on 18/09/2015.
 */
public class IndexDAO {

    public IndexStore getIndex(){
        Connection connection = null;
        IndexStore index = new IndexStore();
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Home");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(rs != null) {
                    index.setId(rs.getInt("id"));
                    index.setTitle(rs.getString("title"));
                    index.setSlug(rs.getString("slug"));
                    index.setDescription(rs.getString("description"));
                    index.setNavigation(rs.getString("navigation"));
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
        return index;
    }

    public IndexStore updateIndex(IndexStore indexStore){
        Connection connection = null;
        IndexStore index = new IndexStore();
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE Home set title= ?, description =? ");
            ps.setString(1,indexStore.getTitle());
            ps.setString(2,indexStore.getDescription());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(rs != null) {
                    index.setId(rs.getInt("id"));
                    index.setTitle(rs.getString("title"));
                    index.setSlug(rs.getString("slug"));
                    index.setDescription(rs.getString("description"));
                    index.setNavigation(rs.getString("navigation"));
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
        return index;
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
