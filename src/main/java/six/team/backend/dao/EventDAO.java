package six.team.backend.dao;

import six.team.backend.store.EventStore;
import six.team.backend.store.UserStore;

import java.sql.*;
import java.util.LinkedList;

/**
 * Created by Gareth on 16/09/2015.
 */
public class EventDAO {


    private static Connection getDBConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String db = "jdbc:mysql://46.101.32.73:3306/enterprisegym";
            connection = DriverManager.getConnection(db,"admin","admin");


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }


    public boolean addEvent(EventStore event){
        Connection connection = null;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Events (event_id,event_title,event_location,event_description,event_points,event_venue,event_startdate,event_enddate) VALUES(?,?,?,?,?,?,?,?)");
            ps.setInt(1, event.getId());
            ps.setString(2, event.getName());
            ps.setString(3, event.getLocation());
            ps.setString(4, event.getDescription());
            ps.setInt(5, event.getPoints());
            ps.setString(6, event.getVenue());
            ps.setDate(7, new java.sql.Date(event.getStartDate().getTime()));
            ps.setDate(8,new java.sql.Date(event.getEndDate().getTime()));
            int result = ps.executeUpdate();
            if(result ==1){
                return true;
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
        return false;
    }

    public LinkedList<EventStore> list(){
        LinkedList<EventStore> events = new LinkedList<EventStore>();
        Connection connection = null;

        try {
            connection = getDBConnection();

            PreparedStatement ps = connection.prepareStatement("select* from events");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EventStore event = new EventStore();
                event.setName(rs.getString("event_title"));
                event.setDescription(rs.getString("event_description"));
                event.setId(rs.getInt("event_id"));
                event.setLocation(rs.getString("event_location"));
                event.setVenue(rs.getString("event_venue"));
                event.setPoints(rs.getInt("points"));
                events.add(event);
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
        return events;
    }

    public EventStore getEvent(int eventid){

        Connection connection = null;
        EventStore event= new EventStore();

        try {
            connection = getDBConnection();

            PreparedStatement ps = connection.prepareStatement("select* from events where event_id=?");
            ps.setInt(1,eventid);
            ResultSet rs = ps.executeQuery();
            event.setId(eventid);
            event.setName(rs.getString("event_title"));
            event.setDescription(rs.getString("event_description"));
            event.setId(rs.getInt("event_id"));
            event.setLocation(rs.getString("event_location"));
            event.setVenue(rs.getString("event_venue"));
            event.setPoints(rs.getInt("points"));


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
        return event;
    }

}
