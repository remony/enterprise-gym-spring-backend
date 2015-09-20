package six.team.backend.controller;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.model.Auth;
import six.team.backend.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gareth on 18/09/2015.
 */

    @Controller
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value= "/auth")
    public class AuthenticationController {
    private final static Logger logger = Logger.getLogger(AuthenticationController.class);

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> authenticate(HttpServletRequest request,HttpServletResponse res) {
        String token = request.getHeader("token");
        String username =request.getHeader("username");
        boolean isAuthorised = Auth.verify(token, username);
        JSONObject details = new JSONObject();
        details.put("authenticated", isAuthorised);
        JSONArray array = new JSONArray();
        array.put(details);
        JSONObject object = new JSONObject();
        object.put("user_auth", array);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
    }
}