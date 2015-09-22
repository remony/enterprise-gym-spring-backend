package six.team.backend.dao;

import six.team.backend.store.CommentStore;
import six.team.backend.store.UserGroupsStore;

import java.sql.*;
import java.util.LinkedList;

/**
 * Created by Anna on 22/09/2015.
 */
public class UserGroupDAO {

    public static boolean add(UserGroupsStore groups){
        Connection connection = null;
        boolean success=false;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("insert into RolePermissions (usergroup,eventsadd,eventsdelete,eventsedit,eventsview,newsadd,newsdelete,newsedit,newsview,pagesadd,pagesdelete,pagesedit,pagesview) values (?,?,?,?,?,?,?,?,?,?,?,?,?) ");
            ps.setString(1, groups.getUsergroup());
            ps.setInt(2, groups.getEventsadd());
            ps.setInt(3, groups.getEventsdelete());
            ps.setInt(4, groups.getEventsedit());
            ps.setInt(5, groups.getEventsview());
            ps.setInt(6, groups.getNewsadd());
            ps.setInt(7, groups.getNewsdelete());
            ps.setInt(8, groups.getNewsedit());
            ps.setInt(9, groups.getNewsview());
            ps.setInt(10, groups.getPagesadd());
            ps.setInt(11, groups.getPagesdelete());
            ps.setInt(12, groups.getPagesedit());
            ps.setInt(13, groups.getPagesview());
            ps.executeUpdate();
            success=true;

        } catch (SQLException e) {
            success=false;
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

    public static boolean update(UserGroupsStore groups){
        Connection connection = null;
        boolean success=false;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("update  RolePermissions set usergroup=?,eventsadd=?,eventsdelete=?,eventsedit=?,eventsview=?,newsadd=?,newsdelete=?,newsedit=?,newsview=?,pagesadd=?,pagesdelete=?,pagesedit=?,pagesview=? ");
            ps.setString(1, groups.getUsergroup());
            ps.setInt(2, groups.getEventsadd());
            ps.setInt(3, groups.getEventsdelete());
            ps.setInt(4, groups.getEventsedit());
            ps.setInt(5, groups.getEventsview());
            ps.setInt(6, groups.getNewsadd());
            ps.setInt(7, groups.getNewsdelete());
            ps.setInt(8, groups.getNewsedit());
            ps.setInt(9, groups.getNewsview());
            ps.setInt(10, groups.getPagesadd());
            ps.setInt(11, groups.getPagesdelete());
            ps.setInt(12, groups.getPagesedit());
            ps.setInt(13, groups.getPagesview());
            ps.executeUpdate();
            success=true;

        } catch (SQLException e) {
            success=false;
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

    public static boolean delete(String usergroup){
        Connection connection = null;
        boolean success=false;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("delete from RolePermissions where usergroup=? ");
            ps.setString(1, usergroup);
            ps.executeUpdate();
            success=true;

        } catch (SQLException e) {
            success=false;
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

    public static LinkedList<UserGroupsStore> get(String usergroup){
        Connection connection = null;
        LinkedList<UserGroupsStore> groups =new LinkedList<UserGroupsStore>();
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select * from RolePermissions where usergroup=? ");
            ps.setString(1, usergroup);
             ResultSet rs=ps.executeQuery();
            while(rs.next()) {
                UserGroupsStore group=new UserGroupsStore();
                group.setEventsadd(rs.getInt("eventsadd"));
                group.setEventsedit(rs.getInt("eventsedit"));
                group.setEventsdelete(rs.getInt("eventsdelete"));
                group.setEventsview(rs.getInt("eventsview"));
                group.setNewsadd(rs.getInt("newsadd"));
                group.setNewsdelete(rs.getInt("newsdelete"));
                group.setNewsedit(rs.getInt("newsedit"));
                group.setNewsview(rs.getInt("newsview"));
                group.setPagesadd(rs.getInt("pagesadd"));
                group.setPagesdelete(rs.getInt("pagesdelete"));
                group.setPagesedit(rs.getInt("pagesedit"));
                group.setPagesview(rs.getInt("pagesview"));
                group.setUsergroup(rs.getString("usergroup"));
                groups.add(group);
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
        return groups;
    }

    public static LinkedList<UserGroupsStore> getAll(){
        Connection connection = null;
        LinkedList<UserGroupsStore> groups =new LinkedList<UserGroupsStore>();
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select * from RolePermissions");
            ResultSet rs=ps.executeQuery();
            while(rs.next()) {
                UserGroupsStore group=new UserGroupsStore();
                group.setEventsadd(rs.getInt("eventsadd"));
                group.setEventsedit(rs.getInt("eventsedit"));
                group.setEventsdelete(rs.getInt("eventsdelete"));
                group.setEventsview(rs.getInt("eventsview"));
                group.setNewsadd(rs.getInt("newsadd"));
                group.setNewsdelete(rs.getInt("newsdelete"));
                group.setNewsedit(rs.getInt("newsedit"));
                group.setNewsview(rs.getInt("newsview"));
                group.setPagesadd(rs.getInt("pagesadd"));
                group.setPagesdelete(rs.getInt("pagesdelete"));
                group.setPagesedit(rs.getInt("pagesedit"));
                group.setPagesview(rs.getInt("pagesview"));
                group.setUsergroup(rs.getString("usergroup"));
                groups.add(group);
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
        return groups;
    }

    public boolean titleExists(String usergroup){
        Connection connection = null;
        boolean exists=false;
        try {
            connection = getDBConnection();

            PreparedStatement ps = connection.prepareStatement("select usergroup from RolePermissions where usergroup=?");
            ps.setString(1, usergroup);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                exists=true;
            else
                exists=false;
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
        return exists;
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
