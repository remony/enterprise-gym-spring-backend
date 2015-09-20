package six.team.backend.model;

import six.team.backend.dao.EventDAO;
import six.team.backend.dao.UserDAO;
import six.team.backend.store.EventStore;
import six.team.backend.store.ParticipantStore;
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
        return EventDAO.listEvents();
    }

    public static boolean updateEvent(EventStore event){
        EventDAO eventDAO = new EventDAO();
        return eventDAO.updateEvent(event);
    }

    public static boolean deleteEvent(int eventid){
        EventDAO eventDAO = new EventDAO();
        return eventDAO.deleteEvent(eventid);
    }
    public static boolean signupEvent(ParticipantStore participant){
        EventDAO eventDAO = new EventDAO();
        return eventDAO.signupEvent(participant);
    }

    public static LinkedList<ParticipantStore> getParticipants(int eventid){
        EventDAO eventDAO = new EventDAO();
        return eventDAO.listParticipants(eventid);
    }

    public static LinkedList<ParticipantStore> getUserEvents(int eventid){
        EventDAO eventDAO = new EventDAO();
        return eventDAO.listUserEvents(eventid);
    }
}