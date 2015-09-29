package six.team.backend.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.dao.UserDAO;
import six.team.backend.model.Event;
import six.team.backend.model.Points;
import six.team.backend.model.User;
import six.team.backend.store.ParticipantStore;
import six.team.backend.store.PointStore;
import six.team.backend.store.UserStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

/**
 * Created by Gareth on 23/09/2015.
 */

@Controller
@RequestMapping("/points")
public class PointController {
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,method= RequestMethod.POST, value={"/update"})
    public @ResponseBody
    ResponseEntity<String> updatePoints(HttpServletRequest request,HttpServletResponse res) {
        UserDAO UD = new UserDAO();
        int points = Integer.parseInt(request.getHeader("points"));
        int userid = Integer.parseInt(request.getHeader("username"));
        String category = request.getHeader("category");
        String token = request.getHeader("token");
        boolean pointsAdded = false;
        if (UD.getUserGroupPermissions(UD.getUserGroup(token),"pointsadd")) {
            pointsAdded = Points.updatePoints(userid, points, category);
            JSONObject object = new JSONObject();
            object.put("points", pointsAdded);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }else {
            JSONObject message = new JSONObject();
            message.put("events", "You are unauthorized to update points");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,method= RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> allPoints(HttpServletRequest request,HttpServletResponse res) {
        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        if (UD.getUserGroupPermissions(UD.getUserGroup(token),"pointsadd")) {
            LinkedList<PointStore> points = Points.allPoints();
            JSONObject details = new JSONObject();
            details.put("points", points);
            return new ResponseEntity<String>(details.toString(), HttpStatus.OK);
        }else {
            JSONObject message = new JSONObject();
            message.put("events", "You are unauthorized to view all points");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,method= RequestMethod.POST, value={"/reset" +
            ""})
    public @ResponseBody
    ResponseEntity<String> resetPoints(HttpServletRequest request,HttpServletResponse res) {
        UserDAO UD = new UserDAO();
        int points = Integer.parseInt(request.getHeader("points"));
        int userid = Integer.parseInt(request.getHeader("userid"));
        String category = request.getHeader("category");
        String token = request.getHeader("token");
        boolean pointsReset= false;
        if (UD.getUserGroupPermissions(UD.getUserGroup(token),"pointsadd")) {
            pointsReset = Points.resetPoints();
            JSONObject object = new JSONObject();
            object.put("points", pointsReset);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }else {
            JSONObject message = new JSONObject();
            message.put("events", "You are unauthorized to reset points");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{username}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> showAllUserPoints(@PathVariable(value="username") String id) {
        LinkedList<PointStore> point = Points.getUserPoints(id);
        JSONObject details = new JSONObject();
        details.put("points: ",point);
        return new ResponseEntity<String>(details.toString(), HttpStatus.OK);
    }
}
