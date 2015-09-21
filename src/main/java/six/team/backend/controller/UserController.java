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

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/users", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> printUsers(HttpServletRequest request) {
        LinkedList<UserInfoStore> users = User.getAll();

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        String userGroup = UD.getUserGroup(token);

        if(userGroup.equals("admin") || userGroup.equals("editor")){
            JSONArray array = new JSONArray();

            for (int i = 0; i < users.size(); i++) {
                UserInfoStore user = users.get(i);
                JSONObject details = new JSONObject();

                details.put("id", user.getId());
                details.put("username", user.getUsername());
                details.put("firstname", user.getFirstName());
                details.put("lastname", user.getLastName());
                details.put("contactNo", user.getContactNo());
                details.put("email", user.getEmail());
                details.put("yearofstudy", user.getYearOfStudy());
                details.put("gender", user.getGender());
                details.put("matricNo", user.getMatricNo());
                details.put("country", user.getCountry());
                details.put("university", user.getUniversity());
                details.put("status", user.getStatus());
                details.put("degreeSubject", user.getDegreeSubject());
                details.put("usergroup", user.getUserGroup());
                details.put("regDate", user.getRegDate());
                array.put(details);
            }
                JSONObject UserInfo = new JSONObject();
                UserInfo.put("UserInfo", array);
                return new ResponseEntity<String>(UserInfo.toString(), HttpStatus.OK);
        }else {
            JSONObject message = new JSONObject();
            message.put("user", "You are Unauthorized to view this content");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }

//        PageJsonGen pageJsonGen = new PageJsonGen();
        //Send values to the page json generator, this will return the full json which is sent to the client

        //Information about the page may be needed to be collected from the db, this is for discussion
//        return pageJsonGen.createPageJson("Users", "A list of all registered users", users);
    }




    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/user/{username}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> getAttr(HttpServletRequest request, @PathVariable(value="username") String userName ){

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        String userGroup = UD.getUserGroup(token);
        String tokenUserName = UD.getUserName(token);
        JSONObject details = new JSONObject();
        UserInfoStore user = User.getUser(userName);

        if(userGroup.equals("admin") || userGroup.equals("editor") || tokenUserName.equals(userName)){


            if (UD.userCheck(userName) == true) {
                details.put("id", user.getId());
                details.put("username", user.getUsername());
                details.put("firstname", user.getFirstName());
                details.put("lastname", user.getLastName());
                details.put("contactNo", user.getContactNo());
                details.put("email", user.getEmail());
                details.put("yearofstudy", user.getYearOfStudy());
                details.put("gender", user.getGender());
                details.put("matricNo", user.getMatricNo());
                details.put("country", user.getCountry());
                details.put("university", user.getUniversity());
                details.put("status", user.getStatus());
                details.put("degreeSubject", user.getDegreeSubject());
                details.put("usergroup", user.getUserGroup());
                details.put("regDate", user.getRegDate());
                details.put("bio", user.getBio());

                JSONArray array = new JSONArray();
                array.put(details);
                JSONObject UserInfo = new JSONObject();
                UserInfo.put("UserInfo", array);
                return new ResponseEntity<String>(UserInfo.toString(), HttpStatus.OK);
            }else{
                details.put("user", "User does not exist!");
                return new ResponseEntity<String>(details.toString(), HttpStatus.OK);
            }
        }else {
            details.put("username", user.getUsername());
            details.put("firstname", user.getFirstName());
            details.put("lastname", user.getLastName());
            return new ResponseEntity<String>(details.toString(), HttpStatus.OK);
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