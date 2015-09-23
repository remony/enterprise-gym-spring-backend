package six.team.backend.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.dao.UserDAO;
import six.team.backend.model.Points;
import six.team.backend.model.User;
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
    @RequestMapping(method= RequestMethod.POST, value={"/update"})
    public @ResponseBody
    ResponseEntity<String> updatePoints(HttpServletRequest request,HttpServletResponse res) {
        UserDAO UD = new UserDAO();
        int points = Integer.parseInt(request.getHeader("points"));
        int userid = Integer.parseInt(request.getHeader("userid"));
        String category = request.getHeader("category");
        String token = request.getHeader("token");
        boolean pointsAdded = false;
        if (UD.getUserGroupPermissions(UD.getUserGroup(token),"pointsadd")) {
           pointsAdded =  Points.updatePoints(points, userid, category);
        }
        JSONObject object = new JSONObject();
        object.put("points", pointsAdded);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);


    }
}
