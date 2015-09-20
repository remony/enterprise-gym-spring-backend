package six.team.backend.dao;

import six.team.backend.utils.Config;
import six.team.backend.store.FileStore;

import java.sql.*;
import java.util.LinkedList;
    /*
            Class: FileDAO
            Description: This class handles the database communication for handling files
    */
public class FileDAO {
    /*
            Method: addToDB
            Description: Inserts record of uploaded file into the db
    */

    public void addToDB(int eventID, int newsID, int pageID, Date dateUploaded, String expandedFilename, String filename){
        Connection connection = null;

        try {
            connection = Config.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("insert into Files"
                    + "(filename, expanded_filename, event_id, news_id, page_id, date_uploaded) values"
                    + "(?,?,?,?,?,?)");
            ps.setString(1, filename);
            ps.setString(2, expandedFilename);
            ps.setInt(3, eventID);
            ps.setInt(4, newsID);
            ps.setInt(5, pageID);
            ps.setDate(6, dateUploaded);
            ps.executeUpdate();
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
    }

    /*
            Method: getFileDB
            Description: Gets a file from the database when given a filename
     */

    public FileStore getFileDB(String filename) {
        FileStore file = new FileStore();
        Connection connection = null;

        try {
            connection = Config.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select * from Files where filename = ?");
            ps.setString(1, filename);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                file.addFileStore(rs.getInt("id"), rs.getString("filename"), rs.getString("expanded_filename"), rs.getInt("event_id"), rs.getInt("news_id"), rs.getInt("page_id"), rs.getDate("date_uploaded"));
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
        return file;
    }

    /*
            Method: getAllFiles
            Description: Gets all of the files
     */

    public LinkedList<FileStore> getAllFiles() {
        LinkedList<FileStore> files = new LinkedList<FileStore>();
        Connection connection = null;
        try {
            connection = Config.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select * from Files");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FileStore file = new FileStore();
                file.setFilename(rs.getString("filename"));
                file.setExpanded_filename(rs.getString("expanded_filename"));
                file.setDate_uploaded(rs.getDate("date_uploaded"));
                if (rs.getInt("event_id") != -1) {
                    file.setEvent_id(rs.getInt("event_id"));
                }
                if (rs.getInt("news_id") != -1) {
                    file.setEvent_id(rs.getInt("news_id"));
                }
                if (rs.getInt("page_id") != -1) {
                    file.setPage_id(rs.getInt("page_id"));
                }
                files.add(file);
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
        return files;
    }

    /*
            Method: getAllEventFiles
            Description: Gets all of the files of the event type of the given id
     */

    public LinkedList<FileStore> getAllEventFiles(String id) {
        LinkedList<FileStore> files = new LinkedList<FileStore>();
        Connection connection = null;
        try {
            connection = Config.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select * from Files where event_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FileStore file = new FileStore();
                file.setFilename(rs.getString("filename"));
                file.setExpanded_filename(rs.getString("expanded_filename"));
                file.setDate_uploaded(rs.getDate("date_uploaded"));
                if (rs.getInt("event_id") != -1) {
                    file.setEvent_id(rs.getInt("event_id"));
                }
                if (rs.getInt("news_id") != -1) {
                    file.setEvent_id(rs.getInt("news_id"));
                }
                if (rs.getInt("page_id") != -1) {
                    file.setPage_id(rs.getInt("page_id"));
                }
                files.add(file);
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
        return files;
    }
    /*
            Method: getAllPageFiles
            Description: Gets all of the files of the page type of the given id
     */

    public LinkedList<FileStore> getAllPageFiles(String id) {
        LinkedList<FileStore> files = new LinkedList<FileStore>();
        Connection connection = null;
        try {
            connection = Config.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select * from Files where page_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FileStore file = new FileStore();
                file.setFilename(rs.getString("filename"));
                file.setExpanded_filename(rs.getString("expanded_filename"));
                file.setDate_uploaded(rs.getDate("date_uploaded"));
                if (rs.getInt("event_id") != -1) {
                    file.setEvent_id(rs.getInt("event_id"));
                }
                if (rs.getInt("news_id") != -1) {
                    file.setEvent_id(rs.getInt("news_id"));
                }
                if (rs.getInt("page_id") != -1) {
                    file.setPage_id(rs.getInt("page_id"));
                }
                files.add(file);
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
        return files;
    }

    /*
            Method: getAllNewsFiles
            Description: Gets all of the files of the news type of the given id
     */

    public LinkedList<FileStore> getAllNewsFiles(String id) {
        LinkedList<FileStore> files = new LinkedList<FileStore>();
        Connection connection = null;
        try {
            connection = Config.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select * from Files where news_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FileStore file = new FileStore();
                file.setFilename(rs.getString("filename"));
                file.setExpanded_filename(rs.getString("expanded_filename"));
                file.setDate_uploaded(rs.getDate("date_uploaded"));
                if (rs.getInt("event_id") != -1) {
                    file.setEvent_id(rs.getInt("event_id"));
                }
                if (rs.getInt("news_id") != -1) {
                    file.setEvent_id(rs.getInt("news_id"));
                }
                if (rs.getInt("page_id") != -1) {
                    file.setPage_id(rs.getInt("page_id"));
                }
                files.add(file);
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
        return files;
    }

    /*
        Method: removeFromDB
        Description: When given a filename will remove a file from the database

     */
    public boolean removeFromDB(String filename) {
        Connection connection = null;
        try {
            connection = Config.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("delete from Files where filename = ?");
            ps.setString(1, filename);
            ps.executeUpdate();
            return true;
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
        return false;
    }
}