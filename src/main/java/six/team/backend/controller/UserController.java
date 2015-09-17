package six.team.backend.controller;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.PageJsonGen;
import six.team.backend.model.User;
import six.team.backend.store.PageStore;
import six.team.backend.store.UserStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;


@Controller
@RequestMapping("/users")
public class UserController {
    private final static Logger logger = Logger.getLogger(UserController.class);

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody PageStore printUsers() {
        LinkedList<UserStore> users = User.getAll();

        PageJsonGen pageJsonGen = new PageJsonGen();
        //Send values to the page json generator, this will return the full json which is sent to the client

        //Information about the page may be needed to be collected from the db, this is for discussion
        return pageJsonGen.createPageJson("Users", "A list of all registered users", users);

    }
    @RequestMapping(value ="/add", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> getUsers(HttpServletRequest request,HttpServletResponse res) {
        String username=request.getHeader("username");
        String password= request.getHeader("password");
        String token = User.verifyLogin(username, password);
        if(token.equals("LoginFailed")) {
            JSONObject details = new JSONObject();
            details.put("message", "Login Failed");
            JSONArray array = new JSONArray();
            array.put(details);
            JSONObject object = new JSONObject();
            object.put("user_auth", array);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }else{
            JSONObject details = new JSONObject();
            details.put("username", username);
            details.put("token:" , token);
            JSONArray array = new JSONArray();
            array.put(details);
            JSONObject object = new JSONObject();
            object.put("user_auth", array);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }
    }
}