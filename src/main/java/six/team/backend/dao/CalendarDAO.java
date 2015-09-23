package six.team.backend.dao;

import org.apache.commons.lang3.time.DateFormatUtils;
import six.team.backend.model.Calendar;
import six.team.backend.store.CalendarStore;
import six.team.backend.utils.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Gareth on 23/09/2015.
 */
@SuppressWarnings("deprecation")
public class CalendarDAO {

    public LinkedList<CalendarStore> getCalendar(){
        Connection connection = null;
        LinkedList<CalendarStore> calendars = new LinkedList<CalendarStore>();

        try {
            connection = Config.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Events");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(rs != null) {
                    CalendarStore event = new CalendarStore();
                    event.setUrl("#/events/" + rs.getInt("event_id"));
                    event.setStart(DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date(rs.getString("event_startdate"))).toString());
                    event.setEnd(DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date(rs.getString("event_enddate"))));
                    event.setTitle(rs.getString("event_title"));
                    calendars.add(event);
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
        return calendars;
    }
}
