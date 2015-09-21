package six.team.backend.dao;

import six.team.backend.store.CommentStore;
import six.team.backend.store.NewsStore;
import java.sql.*;
import java.util.LinkedList;

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


    public boolean update(NewsStore newsStore, String slug) {
        Connection connection = null;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("update News set title=?,slug=?, text=?, lastupdated=?,permission=? where slug=?");
            ps.setString(1, newsStore.getTitle());
            ps.setString(2, newsStore.getSlug());
            ps.setString(3, newsStore.getText());
            java.sql.Date sqlDate = new java.sql.Date(newsStore.getLastedited().getTime());
            ps.setDate(4, sqlDate);
            ps.setString(5, newsStore.getPermission());
            ps.setString(6, slug);
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



    public boolean delete(String slug) {

        Connection connection = null;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("delete from News where slug=?");
            ps.setString(1, slug);
            ps.executeUpdate();
            deleteComments(slug);
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




    public NewsStore get(String slug) {
        NewsStore newsStore = new NewsStore();
        Connection connection = null;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select newsid, title,text,permission,slug,lastupdated,datecreated from News where slug=?");
            ps.setString(1, slug);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newsStore.setId(rs.getInt("newsid"));
                newsStore.setTitle(rs.getString("title"));
                newsStore.setText(rs.getString("text"));
                newsStore.setPermission(rs.getString("permission"));
                newsStore.setSlug(rs.getString("slug"));
                newsStore.setLastedited(rs.getDate("lastupdated"));
                newsStore.setDateCreated(rs.getDate("datecreated"));
                newsStore.setComments(getAllComments(slug));
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
                news.setComments(getAllComments(rs.getString("slug")));
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
   public boolean titleExists(String title){
       Connection connection = null;
       boolean exists=false;
       try {
           connection = getDBConnection();

           PreparedStatement ps = connection.prepareStatement("select newsid from News where title=?");
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



    // code for CRUD for comments

    public static boolean addComment(CommentStore comment){
        Connection connection = null;
        boolean success=false;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("insert into NewsComments (text, slug, author, date) values (?,?,?,?) ");
            ps.setString(1, comment.getText());
            ps.setString(2, comment.getSlug());
            ps.setString(3, comment.getAuthor());
            java.sql.Date sqlDate = new java.sql.Date(comment.getDate().getTime());
            ps.setDate(4, sqlDate);
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

    public static boolean deleteComment(int commentid){
        Connection connection = null;
        boolean success=false;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("delete from NewsComments where commentid=?");
            ps.setInt(1, commentid);
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
    public static boolean deleteComments(String slug){
        Connection connection = null;
        boolean success=false;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("delete from NewsComments where slug=?");
            ps.setString(1, slug);
            ps.executeUpdate();
            success=true;

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
    public static boolean editComment(int commentid,String text){

        Connection connection = null;
        boolean success=false;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("update NewsComments set text=? where commentid=?");
            ps.setString(1, text);
            ps.setInt(2, commentid);
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
    public static LinkedList<CommentStore> getAllComments(String slug){
        Connection connection = null;
        boolean success=false;
        LinkedList<CommentStore> comments= new LinkedList<CommentStore>() ;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select * from NewsComments where slug=?");
            ps.setString(1, slug);
            ResultSet rs= ps.executeQuery();

            while(rs.next())
            {
                CommentStore comment= new CommentStore();
                comment.setAuthor(rs.getString("author"));
                comment.setSlug(rs.getString("slug"));
                comment.setText(rs.getString("text"));
                comment.setCommentid(rs.getInt("commentid"));
                comment.setDate(rs.getDate("date"));
                comments.add(comment);
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
        return comments;
    }

}