package six.team.backend.dao;

import six.team.backend.store.IndexStore;
import six.team.backend.utils.Config;

import java.sql.*;

/**
 * Created by Gareth on 18/09/2015.
 */
public class IndexDAO {

    public IndexStore getIndex(){
        Connection connection = null;
        IndexStore index = new IndexStore();
        try {
            connection = Config.getDBConnection();
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

    public boolean updateIndex(IndexStore index){
        Connection connection = null;
        boolean success = false;
        try {
            connection = Config.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("Update HOME set title =? , description=? where id = ?");
            ps.setString(1,index.getTitle());
            ps.setString(2,index.getDescription());
            ps.setInt(3,index.getId());
            int result = ps.executeUpdate();
            if(result ==1){
                success = true;
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
        return success;
    }
}
