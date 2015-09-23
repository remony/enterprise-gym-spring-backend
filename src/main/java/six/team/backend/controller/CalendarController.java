package six.team.backend.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.model.Calendar;
import six.team.backend.model.User;
import six.team.backend.store.CalendarStore;
import six.team.backend.store.UserStore;

import java.util.LinkedList;

/**
 * Created by Gareth on 23/09/2015.
 */
@Controller
@RequestMapping("/calendar")
public class CalendarController {
        @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.GET)
        public @ResponseBody
        ResponseEntity<String> getCalendar() {
            LinkedList<CalendarStore> calendar = Calendar.getCalendar();
            JSONObject object = new JSONObject();
            object.put("calendar", calendar);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);


        }
}
