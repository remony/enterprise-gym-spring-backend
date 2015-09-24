package six.team.backend.controller;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import six.team.backend.PageJsonGen;
import six.team.backend.dao.UserDAO;
import six.team.backend.model.Event;
import six.team.backend.model.Points;
import six.team.backend.store.EventStore;
import six.team.backend.store.PageStore;
import six.team.backend.store.ParticipantStore;
import six.team.backend.store.UpcomingStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Created by Gareth on 16/09/2015.
 */

@Controller
@RequestMapping("/events")
public class EventController {
        private final static Logger logger = Logger.getLogger(EventController.class);

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> allEvents() {
        LinkedList<EventStore> events = Event.getAll();
        JSONObject details = new JSONObject();
        details.put("events",events);
        return new ResponseEntity<String>(details.toString(), HttpStatus.OK);

    }
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/insert",method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> addEvent(HttpServletRequest request,HttpServletResponse res) {
        EventStore event = new EventStore();
        event.setLocation(request.getHeader("location"));
        event.setName(request.getHeader("name"));
        event.setPoints(Integer.parseInt(request.getHeader("points")));
        event.setVenue(request.getHeader("venue"));
        event.setDescription(request.getHeader("description"));
        event.setStartDate(request.getHeader("startdate"));
        event.setEndDate(request.getHeader("enddate"));
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss z",
                Locale.ENGLISH);
        try{
            Date date = sdf.parse(request.getHeader("startdate"));
            event.setOrderStartDate(date);
        }catch(ParseException e){
            System.out.println(e);
        }
        event.setPoints_category(request.getHeader("points_category"));
        boolean isCreated = Event.createEvent(event);
        JSONObject createObject = new JSONObject();
        createObject.put("message", "Event: " + request.getHeader("name") + " was successfully created");
        if(isCreated) {
            createObject.put("message", "Event: " + request.getHeader("name") + " was successfully created");
            return new ResponseEntity<String>(createObject.toString(), HttpStatus.ACCEPTED);
        }else{
            createObject.put("message", "Event: " + request.getHeader("name") + " was not successfully created");
            return new ResponseEntity<String>(createObject.toString(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/{eventid}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> getAttr(@PathVariable(value="eventid") String id ) {
        EventStore event = Event.getEvent(Integer.parseInt(id));
        if(event.getName()!=null) {
            JSONObject details = new JSONObject();
            details.put("id", id);
            details.put("title" , event.getName());
            details.put("location", event.getLocation());
            details.put("venue", event.getVenue());
            details.put("points" , event.getPoints());
            details.put("startdate", event.getStartDate());
            details.put("enddate", event.getEndDate());
            details.put("description", event.getDescription());
            details.put("points_category", event.getPoints_category());
            JSONArray array = new JSONArray();
            array.put(details);
            JSONObject eventInfo = new JSONObject();
            eventInfo.put("event", array);
            return new ResponseEntity<String>(eventInfo.toString(), HttpStatus.OK);
        }else{
            JSONObject eventInfo = new JSONObject();
            JSONObject notfound = new JSONObject();
            notfound.put("Message", "Not Found");
            eventInfo.put("Event: " , notfound );
            return new ResponseEntity<String>(eventInfo.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/update/{eventid}", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> updateEvent(@PathVariable(value="eventid") String id ,HttpServletRequest request,HttpServletResponse res) {
        EventStore event = new EventStore();
        event.setId(Integer.parseInt(id));
        event.setLocation(request.getHeader("location"));
        event.setName(request.getHeader("name"));
        event.setPoints(Integer.parseInt(request.getHeader("points")));
        event.setVenue(request.getHeader("venue"));
        event.setDescription(request.getHeader("description"));
        event.setStartDate(request.getHeader("startdate"));
        event.setEndDate(request.getHeader("enddate"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try{
            event.setOrderStartDate(formatter.parse(request.getHeader("startdate")));
        }catch(ParseException e){
            System.out.println(e);
        }
        event.setPoints_category(request.getHeader("points_category"));
        boolean isUpdated = Event.updateEvent(event);
        JSONObject createObject = new JSONObject();
        if(isUpdated) {
            createObject.put("message", "Event ID: " + id + " was successfully updated");
            return new ResponseEntity<String>(createObject.toString(), HttpStatus.ACCEPTED);
        }else{
            createObject.put("message", "Event ID: " + id + " was not successfully updated");
            return new ResponseEntity<String>(createObject.toString(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/delete/{eventid}", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> deleteEvent(@PathVariable(value="eventid") String id){
        boolean isDeleted = Event.deleteEvent(Integer.parseInt(id));
        JSONObject createObject = new JSONObject();
        if(isDeleted) {
            createObject.put("message", "Event ID: " + id + " was successfully deleted");
            return new ResponseEntity<String>(createObject.toString(), HttpStatus.ACCEPTED);
        }else{
            createObject.put("message", "Event ID: " + id + " was not successfully deleted");
            return new ResponseEntity<String>(createObject.toString(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{eventid}/signup", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> signupForEvent(@PathVariable(value="eventid") String id ,HttpServletRequest request,HttpServletResponse res) {
            ParticipantStore participant = new ParticipantStore();
            participant.setAttended(0);
            participant.setEvent_id(Integer.parseInt(id));

            UserDAO UD = new UserDAO();
            String token = request.getHeader("token");
            participant.setUserid(UD.getUserID(token));
            //participant.setUserid(Integer.parseInt(request.getHeader("userid")));
            boolean signedUp = Event.signupEvent(participant);
        JSONObject createObject = new JSONObject();
        if(signedUp) {
            createObject.put("message", "Signed Up successfully for Event id:" + id);
            return new ResponseEntity<String>(createObject.toString(), HttpStatus.ACCEPTED);
        }else{
            createObject.put("message", "You are already signed up for this event:" + id);
            return new ResponseEntity<String>(createObject.toString(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{eventid}/participants", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> showParticpants(@PathVariable(value="eventid") String id, HttpServletRequest request) {
        LinkedList<ParticipantStore> participant;
        UserDAO UD = new UserDAO();
        Boolean permissions = false;
        String token = request.getHeader("token");

        if (UD.getUserGroupPermissions(UD.getUserGroup(token),"attendanceedit")) {
            permissions = true;
        }
        participant = Event.getParticipants(permissions,Integer.parseInt(id));
        JSONObject details = new JSONObject();
        details.put("participants",participant);
        return new ResponseEntity<String>(details.toString(), HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{eventid}/participants", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> updateAttendance(@PathVariable(value="eventid") String id, HttpServletRequest request,HttpServletResponse res) {

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        int attended = Integer.parseInt(request.getHeader("attendance"));
        String category = request.getHeader("category");
        int points  = Integer.parseInt(request.getHeader("points"));

        if (UD.getUserGroupPermissions(UD.getUserGroup(token),"attendanceedit")) {

            if (Event.updateAttendance(Integer.parseInt(id), Integer.parseInt(request.getHeader("attendeeid")), Integer.parseInt(request.getHeader("attendance")))) {
                if(attended ==1) {
                    Points.updatePoints(Integer.parseInt(request.getHeader("attendeeid")),points,category);
                }
                else{
                    Points.updatePoints(Integer.parseInt(request.getHeader("attendeeid")),-points,category);
                }
                LinkedList<ParticipantStore> participant;
                participant = Event.getParticipants(true, Integer.parseInt(id));
                JSONObject details = new JSONObject();
                details.put("Participants: ", participant);
                return new ResponseEntity<String>(details.toString(), HttpStatus.OK);
            } else {
                JSONObject createObject = new JSONObject();
                createObject.put("message", "Attendance could not be updated");
                return new ResponseEntity<String>(createObject.toString(), HttpStatus.FORBIDDEN);
            }
        }else{
            JSONObject createObject = new JSONObject();
            createObject.put("message", "You do not have the permissions to send this request");
            return new ResponseEntity<String>(createObject.toString(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/user/{username}", method = RequestMethod.GET)
         public @ResponseBody ResponseEntity<String> showAllUserEvents(@PathVariable(value="username") String id) {
        LinkedList<ParticipantStore> participant;
        LinkedList<EventStore> events = new LinkedList<EventStore>();
        participant = Event.getUserEvents(id);
        JSONObject details = new JSONObject();
        for(int i =0; i<participant.size(); i++){
            EventStore  event =   Event.getEvent(participant.get(i).getEvent_id());
            events.add(event);
        }
        details.put("events",events);

        return new ResponseEntity<String>(details.toString(), HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/upcoming", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> showUpcoming(HttpServletRequest request,HttpServletResponse res) {
       int resultNumber;
        if(request.getHeader("number")!=null){
            resultNumber = Integer.parseInt(request.getHeader("number"));
        }
        else{
            resultNumber =5;
        }
        LinkedList<UpcomingStore> upcoming;
        upcoming = Event.getUpcomingEvents(resultNumber);
        JSONObject details = new JSONObject();
        details.put("upcoming", upcoming);
        return new ResponseEntity<String>(details.toString(), HttpStatus.OK);
    }
}
