package six.team.backend.model;

import six.team.backend.dao.CalendarDAO;
import six.team.backend.store.CalendarStore;

import java.util.LinkedList;

/**
 * Created by Gareth on 23/09/2015.
 */
public class Calendar {
    public static LinkedList<CalendarStore> getCalendar(){
        CalendarDAO calendarDAO = new CalendarDAO();
        return calendarDAO.getCalendar();

    }
}
