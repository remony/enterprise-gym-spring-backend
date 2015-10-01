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
import six.team.backend.model.User;
import six.team.backend.store.PageStore;
import six.team.backend.store.UserLoginStore;
import six.team.backend.store.UserInfoStore;
import six.team.backend.store.UserStore;
import six.team.backend.utils.Email;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;


@Controller
//@RequestMapping("/users")
public class UserController {
    //this endpoint returns a list of all the users
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/users", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> printUsers(HttpServletRequest request) {
        LinkedList<UserInfoStore> users = User.getAll();

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");

        if(UD.getUserGroupPermissions(UD.getUserGroup(token),"viewAllUsers")){
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
    }




    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/user/{username}", method = RequestMethod.GET)
    //this endpoint returns the user profile for the username given in the username
    public @ResponseBody ResponseEntity<String> getAttr(HttpServletRequest request, @PathVariable(value="username") String userName ){

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        String userGroup = UD.getUserGroup(token);
        String tokenUserName = UD.getUserName(token);
        JSONObject details = new JSONObject();
        UserInfoStore user = User.getUser(userName);

        if(UD.getUserGroupPermissions(UD.getUserGroup(token),"viewAllUsers") || tokenUserName.equals(userName)){


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
                details.put("young_es", user.isYoung_e_s());


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

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value ="/user/login", method = RequestMethod.POST)
    //this endpoint is used for the user to login into the website
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
            details.put("token" , user.getToken());
            details.put("usergroup", user.getUsergroup());
            JSONArray array = new JSONArray();
            array.put(details);
            JSONObject object = new JSONObject();
            object.put("user_auth", array);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }
    }
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value ="/user/{username}/passwordreset", method = RequestMethod.POST)
    public @ResponseBody
    //this endpoint is used for a user to reset there password
    ResponseEntity<String> passwordReset(HttpServletRequest request,HttpServletResponse res, @PathVariable String username) {
        String password= request.getHeader("password");
        String newpassword= request.getHeader("newpassword");
        String email= request.getHeader("email");
        JSONObject object = new JSONObject();
        UserLoginStore user = User.verifyLogin(username, password);
        if(user.getMessage().equals("LoginFailed")) {
            object.put("change", "wrongpassword");
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }
        else {
            boolean success = User.resetPassword(username, newpassword);
            object.put("change", success);
            if(success==true){
                Email emailclass =new Email();
           //     emailclass.sendEmail("notplastic@mail.ru","Password change","Your password was changed");
            }
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }

    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/user/{username}/delete", method = RequestMethod.POST)
    //this endpoint is used to delete the user
    public @ResponseBody ResponseEntity<String> deleteUser(HttpServletRequest request, @PathVariable(value="username") String userName ){

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");

        if(UD.getUserGroupPermissions(UD.getUserGroup(token),"userdelete")) {
            boolean success = User.delete(Integer.parseInt(request.getHeader("userid")));
            JSONObject object = new JSONObject();
            object.put("deleteuser", success);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }else {
            JSONObject message = new JSONObject();
            message.put("user", "You are Unauthorized to delete a user");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/user/{username}", method = RequestMethod.POST)
    //this endpoint is used to update the user details from the data passed in the request headers
    public @ResponseBody ResponseEntity<String> updateUser(HttpServletRequest request, @PathVariable(value="username") String username,@RequestBody String bio) {

        JSONObject object = new JSONObject();
        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");

        if (UD.getUserGroupPermissions(UD.getUserGroup(token), "useredit")) {
            if (username.equals(request.getHeader("username")) || User.getUser(request.getHeader("username")) == null) {
                boolean success = User.update(Integer.parseInt(request.getHeader("userid")), request.getHeader("username"),
                        request.getHeader("firstname"), request.getHeader("lastname"),
                        request.getHeader("gender"), request.getHeader("email"), request.getHeader("contactnumber"), request.getHeader("country"),
                        request.getHeader("university"), request.getHeader("status"), request.getHeader("subject"),
                        request.getHeader("matricnumber"), Integer.parseInt(request.getHeader("young_es")), request.getHeader("usergroup"),
                        Integer.parseInt(request.getHeader("yearofstudy")), bio);
                object = new JSONObject();
                object.put("updateuser", success);
                return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
            } else {
                object = new JSONObject();
                object.put("updateuser", "User exists");
                return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
            }

        } else {
            JSONObject message = new JSONObject();
            message.put("user", "You are Unauthorized to view this content");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }
}