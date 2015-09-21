package six.team.backend.dao;

import six.team.backend.store.CommentStore;
import six.team.backend.store.NewsStore;
import six.team.backend.store.PageStore;

import java.sql.*;
import java.util.LinkedList;

public class PagesDAO {

    public boolean addPage(PageStore page) {
        boolean success=false;
        Connection connection = null;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("insert into Pages (title, description, text,permission,slug,parentslug, pageorder) values (?,?,?,?,?,?,?)");
            ps.setString(1, page.getTitle());
            ps.setString(2, page.getDescription());
            ps.setString(3, page.getText());
            ps.setString(4, page.getPermission());
            ps.setString(5, page.getSlug());
            ps.setString(6, page.getParentSlug());
            ps.setInt(7, page.getOrder());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            success=false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    success=true;
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return success;

    }

    public boolean deletePage(String slug) {

        Connection connection = null;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("delete from Pages where slug=?");
            ps.setString(1, slug);
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



    public boolean editPage(PageStore page,String slug) {
        boolean success=false;
        Connection connection = null;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("update Pages set title=?, description=?, text=?,permission=?,slug=?,parentslug=?,pageorder=? where slug=?");
            ps.setString(1, page.getTitle());
            ps.setString(2, page.getDescription());
            ps.setString(3, page.getText());
            ps.setString(4, page.getPermission());
            ps.setString(5, page.getSlug());
            ps.setString(6, page.getParentSlug());
            ps.setInt(7, page.getOrder());
            ps.setString(8, slug);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    success=true;
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return success;

    }

    public static LinkedList<PageStore> getAll(){
        Connection connection = null;
        boolean success=false;
        LinkedList<PageStore> pages= new LinkedList<PageStore>() ;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select * from Pages where parentslug=?");
            ps.setString(1,"main");
            ResultSet rs= ps.executeQuery();

            while(rs.next())
            {
                PageStore page= new PageStore();
                page.setTitle(rs.getString("title"));
                page.setParentSlug(rs.getString("parentslug"));
                page.setSlug(rs.getString("slug"));
                page.setText(rs.getString("text"));
                page.setDescription(rs.getString("description"));
                page.setPermission(rs.getString("permission"));
                page.setOrder(rs.getInt("pageorder"));
                page.setPageid(rs.getInt("pageid"));
                page.setSubpages(getSubpages(page.getSlug()));
                pages.add(page);
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
        return pages;
    }

    public static LinkedList<PageStore> get(String slug){
        Connection connection = null;
        boolean success=false;
        LinkedList<PageStore> pages= new LinkedList<PageStore>() ;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select * from Pages where slug=?");
            ps.setString(1, slug);
            ResultSet rs= ps.executeQuery();

            while(rs.next())
            {
                PageStore page= new PageStore();
                page.setParentSlug(rs.getString("parentslug"));
                page.setTitle(rs.getString("title"));
                page.setSlug(rs.getString("slug"));
                page.setText(rs.getString("text"));
                page.setDescription(rs.getString("description"));
                page.setPermission(rs.getString("permission"));
                page.setOrder(rs.getInt("pageorder"));
                page.setPageid(rs.getInt("pageid"));
                page.setSubpages(getSubpages(page.getSlug()));
                pages.add(page);
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
        return pages;
    }

    public static LinkedList<PageStore> getSubpages(String slug){
        Connection connection = null;
        boolean success=false;
        LinkedList<PageStore> pages= new LinkedList<PageStore>() ;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select * from Pages where parentslug=?");
            ps.setString(1, slug);
            ResultSet rs= ps.executeQuery();
            while(rs.next())
            {
                PageStore page= new PageStore();
                page.setTitle(rs.getString("title"));
                page.setParentSlug(rs.getString("parentslug"));
                page.setSlug(rs.getString("slug"));
                page.setText(rs.getString("text"));
                page.setDescription(rs.getString("description"));
                page.setPermission(rs.getString("permission"));
                page.setOrder(rs.getInt("pageorder"));
                page.setPageid(rs.getInt("pageid"));
                page.setSubpages(getSubpages(page.getSlug()));
                pages.add(page);
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
        return pages;
    }


    public boolean titleExists(String title){
        Connection connection = null;
        boolean exists=false;
        try {
            connection = getDBConnection();

            PreparedStatement ps = connection.prepareStatement("select pageid from Pages where title=?");
            ps.setString(1, title);
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
