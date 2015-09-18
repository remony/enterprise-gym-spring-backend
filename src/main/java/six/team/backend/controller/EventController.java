package six.team.backend.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import six.team.backend.PageJsonGen;
import six.team.backend.model.Event;
import six.team.backend.store.EventStore;
import six.team.backend.store.PageStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Gareth on 16/09/2015.
 */

@Controller
@RequestMapping("/event")
public class EventController {
        private final static Logger logger = Logger.getLogger(EventController.class);

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody PageStore printUsers() {
        LinkedList<EventStore> events = Event.getAll();
        PageJsonGen pageJsonGen = new PageJsonGen();
        return pageJsonGen.createPageJson("Events", "A list of all registered users", events);

    }
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<EventStore> addEvent(HttpServletRequest request,HttpServletResponse res) {
        EventStore event = new EventStore();
        event.setLocation(request.getHeader("location"));
        event.setName(request.getHeader("name"));
        event.setPoints(Integer.parseInt(request.getHeader("points")));
        event.setVenue(request.getHeader("venue"));
        event.setDescription(request.getHeader("description"));
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
        try{
            event.setStartDate(formatter.parse(request.getHeader("startdate")));
            event.setEndDate(formatter.parse(request.getHeader("enddate")));
        }catch(ParseException e){
            System.out.println(e);
        }


        boolean isCreated = Event.createEvent(event);
        if(isCreated) {
            return new ResponseEntity<EventStore>(event, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<EventStore>(event, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/{eventid}", method = RequestMethod.GET)
    public @ResponseBody EventStore getAttr(@PathVariable(value="eventid") String id ) {
        return Event.getEvent(Integer.parseInt(id));
    }

    @RequestMapping(value = "/update/{eventid}", method = RequestMethod.POST)
    public @ResponseBody EventStore updateEvent(@PathVariable(value="eventid") String id ) {
        return Event.getEvent(Integer.parseInt(id));
    }
}
