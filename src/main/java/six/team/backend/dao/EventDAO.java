package six.team.backend.dao;

import six.team.backend.store.EventStore;
import six.team.backend.store.ParticipantStore;
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
        boolean added = false;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Events (event_id,event_title,event_location,event_description,event_points,event_venue,event_startdate,event_enddate, order_startdate) VALUES(?,?,?,?,?,?,?,?,?)");
            ps.setInt(1,event.getId());
            ps.setString(2, event.getName());
            ps.setString(3, event.getLocation());
            ps.setString(4, event.getDescription());
            ps.setInt(5, event.getPoints());
            ps.setString(6, event.getVenue());
            ps.setString(7, event.getStartDate());
            ps.setString(8,event.getEndDate());
            ps.setDate(9, new java.sql.Date(event.getOrderStartDate().getTime()));
            int result = ps.executeUpdate();
            if(result ==1){
                added =true;
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
        return added;
    }

    public LinkedList<EventStore> listEvents(){
        LinkedList<EventStore> events = new LinkedList<EventStore>();
        Connection connection = null;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select* from Events order by order_startdate DESC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EventStore event = new EventStore();
                event.setName(rs.getString("event_title"));
                event.setDescription(rs.getString("event_description"));
                event.setId(rs.getInt("event_id"));
                event.setLocation(rs.getString("event_location"));
                event.setVenue(rs.getString("event_venue"));
                event.setPoints(rs.getInt("event_points"));
                event.setStartDate(rs.getString("event_startdate"));
                event.setEndDate((rs.getString("event_enddate")));
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

            PreparedStatement ps = connection.prepareStatement("select* from Events where event_id=?");
            ps.setInt(1, eventid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if (rs != null) {
                    event.setId(eventid);
                    event.setName(rs.getString("event_title"));
                    event.setDescription(rs.getString("event_description"));
                    event.setId(rs.getInt("event_id"));
                    event.setLocation(rs.getString("event_location"));
                    event.setVenue(rs.getString("event_venue"));
                    event.setPoints(rs.getInt("event_points"));
                    event.setStartDate(rs.getString("event_startdate"));
                    event.setEndDate((rs.getString("event_enddate")));
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
        return event;
    }

    public boolean updateEvent(EventStore event){
        Connection connection = null;
        boolean updated =false;

        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE Events set event_title=?,event_location=?,event_description=?,event_points=?,event_venue=?,event_startdate=?,event_enddate=?, order_startdate=? where event_id = ?");
            ps.setString(1, event.getName());
            ps.setString(2, event.getLocation());
            ps.setString(3, event.getDescription());
            ps.setInt(4, event.getPoints());
            ps.setString(5, event.getVenue());
            ps.setString(6, event.getStartDate());
            ps.setString(7,event.getEndDate());
            ps.setDate(8, new java.sql.Date(event.getOrderStartDate().getTime()));
            ps.setInt(9,event.getId());
            int result = ps.executeUpdate();
            if(result ==1){
                updated = true;
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
        return updated;
    }

    public boolean deleteEvent(int eventid){

        Connection connection = null;
        boolean deleted = false;

        try {
            connection = getDBConnection();

            PreparedStatement ps = connection.prepareStatement("delete from Events where event_id=?");
            ps.setInt(1, eventid);
            int rs = ps.executeUpdate();
            if(rs ==1){
                deleted = true;
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
        return deleted;
    }


    public boolean signupEvent(ParticipantStore participant){

        Connection connection = null;
        boolean signedUp = false;
        if(hasSignedUp(participant) != true){
        try {
            connection = getDBConnection();

            PreparedStatement ps = connection.prepareStatement("insert into Participants (event_id, userid, attended) values (?,?,?);");
            ps.setInt(1, participant.getEvent_id());
            ps.setInt(2,participant.getUserid());
            ps.setInt(3,participant.getAttended());
            int rs = ps.executeUpdate();
            if(rs ==1){
                signedUp = true;
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
        }}
        return signedUp;
    }

    public boolean hasSignedUp(ParticipantStore participant){

        Connection connection = null;
        boolean signedUp = false;

        try {
            connection = getDBConnection();

            PreparedStatement ps = connection.prepareStatement("SELECT userid FROM Participants WHERE event_id = ? AND userid = ?");
            ps.setInt(1, participant.getEvent_id());
            ps.setInt(2, participant.getUserid());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if(rs != null) signedUp = true;
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
        return signedUp;
    }


    public boolean updateAttendance(int eventid, int userid, int newAttended){

        Connection connection = null;
        boolean attended = false;

        try {
            connection = getDBConnection();

            PreparedStatement ps = connection.prepareStatement("UPDATE Participants SET attended = ? WHERE event_id = ? AND userid = ?");
            ps.setInt(1, newAttended);
            ps.setInt(2, eventid);
            ps.setInt(3, userid);
            int rs = ps.executeUpdate();
            if(rs ==1){
                attended = true;
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
        return attended;
    }

    public LinkedList<ParticipantStore> listParticipants(boolean permissions, int eventid){
        LinkedList<ParticipantStore> participants = new LinkedList<ParticipantStore>();
        Connection connection = null;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select* from Participants where event_id = ? order by attended");
            ps.setInt(1,eventid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ParticipantStore participant = new ParticipantStore();
                if (permissions) {
                    participant.setUserid(rs.getInt("userid"));
                    participant.setAttended(rs.getInt("attended"));
                }

                participant.setEvent_id(rs.getInt("event_id"));
                participants.add(collectDetails(participant, rs.getInt("userid")));
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
        return participants;
    }

    public ParticipantStore collectDetails(ParticipantStore participant, int userid){
        Connection connection = null;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("select username, firstname, lastname from Users where userid = ?");
            ps.setInt(1,userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                participant.setUsername(rs.getString("username"));
                participant.setFirstname(rs.getString("firstname"));
                participant.setLastname(rs.getString("lastname"));
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
        return participant;
    }

    public LinkedList<ParticipantStore> listUserEvents(String username){
        LinkedList<ParticipantStore> events = new LinkedList<ParticipantStore>();
        Connection connection = null;
        int userid =0;
        try {
            connection = getDBConnection();
            PreparedStatement ps1 = connection.prepareStatement("select* from Users where username = ?");
            ps1.setString(1,username);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                if(rs1!=null){
                    userid = rs1.getInt("userid");
                    System.out.println(userid);
                }
            }
            PreparedStatement ps = connection.prepareStatement("select* from Participants where userid = ? order by attended");
            ps.setInt(1,userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ParticipantStore event = new ParticipantStore();
                event.setUserid(rs.getInt("userid"));
                event.setEvent_id(rs.getInt("event_id"));
                event.setAttended(rs.getInt("attended"));
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

}
