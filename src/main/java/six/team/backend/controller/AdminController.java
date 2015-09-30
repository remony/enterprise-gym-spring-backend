package six.team.backend.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.PageJsonGen;
import six.team.backend.dao.UserDAO;
import six.team.backend.model.User;
import six.team.backend.store.PageStore;
import six.team.backend.store.UserStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

/**
 * Created by Anna on 16/09/2015.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    //this endpoint returns a list of all unauthorised users
    @RequestMapping(method=RequestMethod.GET, value={"/users"})
        public @ResponseBody ResponseEntity<String> getUnauthorised(HttpServletRequest request) {

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");

        if (UD.getUserGroupPermissions(UD.getUserGroup(token), "userview")) { //checks the user has permission
            LinkedList<UserStore> users = User.getAllUnauthorised();
            JSONObject object = new JSONObject();
            object.put("unauthorisedusers", users);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);

        } else { //returns an unauthorised json and http status if not authorised
            JSONObject message = new JSONObject();
            message.put("user", "You are Unauthorized to view this content");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(method=RequestMethod.POST, value={"/users"})
    //post request method to approve the users and set the correct user group
    public @ResponseBody ResponseEntity<String> approveUser(HttpServletRequest request,HttpServletResponse res) {
        String approved_id = request.getHeader("approvedId");
        String approved_group = request.getHeader("approvedGroup");
        String approved_status = request.getHeader("approvedStatus"); // approved
        User user = new User();
        boolean success;

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");

        if (UD.getUserGroupPermissions(UD.getUserGroup(token), "useredit")) {
            if (approved_status.equals("approved")) {
                success = user.approve(Integer.parseInt(approved_id), approved_group);
            } else {

                success = user.delete(Integer.parseInt(approved_id));
            }
            LinkedList<UserStore> users = User.getAllUnauthorised();
            JSONObject object = new JSONObject();
            object.put("unauthorisedusers", users);
            if (success)
                return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
            else
                return new ResponseEntity<String>(object.toString(), HttpStatus.valueOf(500));
        } else {
            JSONObject message = new JSONObject();
            message.put("user", "You are Unauthorized to view this content");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }
}
