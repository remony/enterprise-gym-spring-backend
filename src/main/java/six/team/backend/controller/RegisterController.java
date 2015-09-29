package six.team.backend.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.dao.UserDAO;
import six.team.backend.model.User;
import six.team.backend.store.UserStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 * Created by Stan on 9/15/2015.
 */

@Controller
@RequestMapping("/registration")
public class RegisterController {

    // NOTE: to look at line 88 when things get merged: "You should be returning conflict when a user of the same username already exists, your sending a conflict when the user does not have a email and username"

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> registerUser(HttpServletRequest request,HttpServletResponse res) {

        // convert the true/false value for Enterpr. Society to the bit value used in the SQL db
        int young_es_int = 0;
      //  if (request.getHeader("young_es").equals("true"))
           // young_es_int = 1;
        //else
          //  young_es_int = 0;

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String firstname = request.getHeader("firstname");
        String lastname = request.getHeader("lastname");
        String email = request.getHeader("email");
        String contactnumber = request.getHeader("contactnumber");
        String country = request.getHeader("country");
        String university = request.getHeader("university");
        String number = request.getHeader("number");
        String status = request.getHeader("status");
        String subject = request.getHeader("subject");
        int yearofstudy = Integer.parseInt(request.getHeader("yearofstudy"));
        String matricnumber = request.getHeader("matricnumber");
        int young_es = young_es_int;
        String mobile = request.getHeader("mobile");
        String gender = request.getHeader("gender");
        String bio = request.getHeader("bio");

        UserStore userStore = new UserStore();
        userStore.setUsername(username);
        userStore.setPassword(password);
        userStore.setFirstname(firstname);
        userStore.setLastname(lastname);
        userStore.setEmail(email);
        userStore.setContactnumber(contactnumber);
        userStore.setCountry(country);
        userStore.setUniversity(university);
        userStore.setStatus(status);
        userStore.setSubject(subject);
        userStore.setYearofstudy(yearofstudy);
        userStore.setMatricnumber(matricnumber);
        userStore.setYoung_es(young_es);
        userStore.setGender(gender);
        userStore.setRegistration_date(new Date());
        userStore.setBio(bio);

        // for testing:
        UserDAO userDAO = new UserDAO();
        int result = userDAO.Save(userStore);

        // return a JSON object
        JSONObject object = new JSONObject();
        if(result == 0) {
            object.put("message", "User registered");
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }else if(result==1) {
            object.put("message", "Username has already been registered ");
            return new ResponseEntity<String>(object.toString(), HttpStatus.valueOf(401));
        } else if(result ==2){
            object.put("message", "Email has already been registered ");
            return new ResponseEntity<String>(object.toString(), HttpStatus.valueOf(401));
        } else{
            object.put("message", "Unknown Error ");
            return new ResponseEntity<String>(object.toString(), HttpStatus.valueOf(401));
        }

    }

}
