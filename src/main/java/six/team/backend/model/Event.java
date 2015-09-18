package six.team.backend.model;

import six.team.backend.dao.EventDAO;
import six.team.backend.dao.UserDAO;
import six.team.backend.store.EventStore;
import six.team.backend.store.UserStore;

import java.util.LinkedList;

/**
 * Created by Gareth on 16/09/2015.
 */
public class Event {

    public static boolean createEvent(EventStore event) {
        EventDAO eventDAO = new EventDAO();
       return eventDAO.addEvent(event);
    }

    public static EventStore getEvent(int eventId){
        EventDAO eventDAO = new EventDAO();
       return eventDAO.getEvent(eventId);
    }

    public static LinkedList<EventStore> getAll() {
        EventDAO EventDAO = new EventDAO();
        return EventDAO.list();
    }
}
