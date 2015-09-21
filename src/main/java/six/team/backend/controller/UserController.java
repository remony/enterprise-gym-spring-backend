package six.team.backend.controller;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.PageJsonGen;
import six.team.backend.dao.UserDAO;
import six.team.backend.model.User;
import six.team.backend.store.PageStore;
import six.team.backend.store.UserLoginStore;
import six.team.backend.store.UserInfoStore;
import six.team.backend.store.UserStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;


@Controller
//@RequestMapping("/users")
public class UserController {
    private final static Logger logger = Logger.getLogger(UserController.class);

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<LinkedList<UserInfoStore>> printUsers(HttpServletRequest request) {
        LinkedList<UserInfoStore> users = User.getAll();

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        String userGroup = UD.getUserGroup(token);

        if(userGroup.equals("admin") || userGroup.equals("editor")){
            return new ResponseEntity<LinkedList<UserInfoStore>>(users, HttpStatus.OK);
        }else {
            users = new LinkedList<UserInfoStore>();
            return new ResponseEntity<LinkedList<UserInfoStore>>(users, HttpStatus.valueOf(401));
        }

//        PageJsonGen pageJsonGen = new PageJsonGen();
        //Send values to the page json generator, this will return the full json which is sent to the client

        //Information about the page may be needed to be collected from the db, this is for discussion
//        return pageJsonGen.createPageJson("Users", "A list of all registered users", users);
    }




    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<UserInfoStore> getAttr(HttpServletRequest request, @PathVariable(value="username") String userName ){

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        String userGroup = UD.getUserGroup(token);
        String tokenUserName = UD.getUserName(token);

        if(userGroup.equals("admin") || userGroup.equals("editor") || tokenUserName.equals(userName)){
            UserInfoStore user = User.getUser(userName);
            return new ResponseEntity<UserInfoStore>(user, HttpStatus.OK);
        }else {
            UserInfoStore user = new UserInfoStore();
            return new ResponseEntity<UserInfoStore>(user, HttpStatus.valueOf(401));
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value ="/login", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> loginUsers(HttpServletRequest request,HttpServletResponse res) {
        String username= request.getHeader("username");
        String password= request.getHeader("password");
        UserLoginStore user = User.verifyLogin(username, password);

        if(user.getMessage().equals("LoginFailed")) {
            JSONObject details = new JSONObject();
            details.put("message", "Login Failed");
            JSONArray array = new JSONArray();
            array.put(details);
            JSONObject object = new JSONObject();
            object.put("user_auth", array);
            return new ResponseEntity<String>(object.toString(), HttpStatus.UNAUTHORIZED);
        }else  if(user.getMessage().equals("Unapproved")) {
            JSONObject details = new JSONObject();
            details.put("message", "Your account has not yet been approved");
            JSONArray array = new JSONArray();
            array.put(details);
            JSONObject object = new JSONObject();
            object.put("user_auth", array);
            return new ResponseEntity<String>(object.toString(), HttpStatus.UNAUTHORIZED);
        }else{
            JSONObject details = new JSONObject();
            details.put("username", username);
            details.put("token:" , user.getToken());
            details.put("usergroup", user.getUsergroup());
            JSONArray array = new JSONArray();
            array.put(details);
            JSONObject object = new JSONObject();
            object.put("user_auth", array);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }
    }
}