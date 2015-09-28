package six.team.backend.dao;

import six.team.backend.store.CommentStore;
import six.team.backend.store.UserGroupsStore;
import six.team.backend.utils.Config;

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
            connection = Config.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("insert into RolePermissions (usergroup,eventsadd,eventsdelete,eventsedit,eventsview,newsadd,newsdelete,newsedit,newsview,pagesadd,pagesdelete,pagesedit,pagesview,usersadd,usersdelete,usersedit,usersview ,pointsadd, pointsreset, pointsview, commentsedit, quizview, quizadd, quizScore, fileupload, fileedit) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
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
            ps.setInt(14, groups.getUserssadd());
            ps.setInt(15, groups.getUsersdelete());
            ps.setInt(16, groups.getUsersedit());
            ps.setInt(17, groups.getUsersview());
            ps.setInt(18, groups.getPointsadd());
            ps.setInt(19, groups.getPointsreset());
            ps.setInt(20, groups.getPointsview());
            ps.setInt(21, groups.getCommentsedit());
            ps.setInt(22, groups.getQuizview());
            ps.setInt(23, groups.getQuizadd());
            ps.setInt(24,groups.getQuizScore());
            ps.setInt(25, groups.getFileupload());
            ps.setInt(26, groups.getFileedit());
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
            connection = Config.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("update  RolePermissions set usergroup=?,eventsadd=?,eventsdelete=?,eventsedit=?,eventsview=?,newsadd=?,newsdelete=?,newsedit=?,newsview=?,pagesadd=?,pagesdelete=?,pagesedit=?,pagesview=?,usersadd=?,usersdelete=?,usersedit=?,usersview=?, pointsadd=?, pointsreset=?, pointsview=?, commentsedit=?, quizview=?, quizadd=?, quizScore =?, fileupload=?, fileedit=?");
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
            ps.setInt(14, groups.getUserssadd());
            ps.setInt(15, groups.getUsersdelete());
            ps.setInt(16, groups.getUsersedit());
            ps.setInt(17, groups.getUsersview());
            ps.setInt(18, groups.getPointsadd());
            ps.setInt(19, groups.getPointsreset());
            ps.setInt(20, groups.getPointsview());
            ps.setInt(21, groups.getCommentsedit());
            ps.setInt(22, groups.getQuizview());
            ps.setInt(23, groups.getQuizadd());
            ps.setInt(24,groups.getQuizScore());
            ps.setInt(25, groups.getFileupload());
            ps.setInt(26, groups.getFileedit());
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
            connection = Config.getDBConnection();
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
            connection = Config.getDBConnection();
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
                group.setUserssadd(rs.getInt("usersadd"));
                group.setUsersdelete(rs.getInt("usersdelete"));
                group.setUsersedit(rs.getInt("usersedit"));
                group.setUsersview(rs.getInt("usersview"));
                group.setPointsadd(rs.getInt("pointsadd"));
                group.setPointsview(rs.getInt("pointsview"));
                group.setPointsreset(rs.getInt("pointsreset"));
                group.setCommentsedit(rs.getInt("commentsedit"));
                group.setQuizview(rs.getInt("quizview"));
                group.setQuizadd(rs.getInt("quizadd"));
                group.setQuizScore(rs.getInt("quizScore"));
                group.setFileupload(rs.getInt("fileupload"));
                group.setFileedit(rs.getInt("fileedit"));
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
            connection = Config.getDBConnection();
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
                group.setUserssadd(rs.getInt("usersadd"));
                group.setUsersdelete(rs.getInt("usersdelete"));
                group.setUsersedit(rs.getInt("usersedit"));
                group.setUsersview(rs.getInt("usersview"));
                group.setPointsadd(rs.getInt("pointsadd"));
                group.setPointsview(rs.getInt("pointsview"));
                group.setPointsreset(rs.getInt("pointsreset"));
                group.setCommentsedit(rs.getInt("commentsedit"));
                group.setQuizview(rs.getInt("quizview"));
                group.setQuizadd(rs.getInt("quizadd"));
                group.setQuizScore(rs.getInt("quizScore"));
                group.setFileupload(rs.getInt("fileupload"));
                group.setFileedit(rs.getInt("fileedit"));
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
            connection = Config.getDBConnection();

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
}
