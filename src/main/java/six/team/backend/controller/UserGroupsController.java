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
import six.team.backend.PageJsonGen;
import six.team.backend.model.News;
import six.team.backend.model.UserGroups;
import six.team.backend.store.NewsStore;
import six.team.backend.store.UserGroupsStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

/**
 * Created by Anna on 22/09/2015.
 */
@Controller
@RequestMapping("/usergroup")
public class UserGroupsController {
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> printUserGroups() {
        LinkedList<UserGroupsStore> usergroups = UserGroups.getAll();
        JSONObject object = new JSONObject();
        object.put("allgroups", usergroups);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value="{usergroup}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> printUserGroup(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "usergroup") String usergroup ) {
        LinkedList<UserGroupsStore> usergroups = UserGroups.get(usergroup);
        JSONObject object = new JSONObject();
        object.put("group", usergroups);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> addUserGroup(HttpServletRequest request,HttpServletResponse response ) {

        boolean success;
        if(UserGroups.checkValidity(request.getHeader("usergroup"))) {
            JSONObject object = new JSONObject();
            object.put("addgroup", "usergroupexists");
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }
        else {
            success = UserGroups.addUserGroup(request.getHeader("usergroup"),
                    Integer.parseInt(request.getHeader("eventsadd")), Integer.parseInt(request.getHeader("eventsedit")),
                    Integer.parseInt(request.getHeader("eventsdelete")), Integer.parseInt(request.getHeader("eventsview")),
                    Integer.parseInt(request.getHeader("newsadd")), Integer.parseInt(request.getHeader("newsdelete")),
                    Integer.parseInt(request.getHeader("newsedit")), Integer.parseInt(request.getHeader("newsview")),
                    Integer.parseInt(request.getHeader("pagesadd")), Integer.parseInt(request.getHeader("pagesdelete")),
                    Integer.parseInt(request.getHeader("pagesedit")), Integer.parseInt(request.getHeader("pagesview")),
                    Integer.parseInt(request.getHeader("usersadd")), Integer.parseInt(request.getHeader("usersdelete")),
                    Integer.parseInt(request.getHeader("usersedit")), Integer.parseInt(request.getHeader("usersview")),
                    Integer.parseInt(request.getHeader("pointsadd")), Integer.parseInt(request.getHeader("pointsreset")),
                    Integer.parseInt(request.getHeader("pointsview")), Integer.parseInt(request.getHeader("commentsedit")),
                    Integer.parseInt(request.getHeader("quizview")), Integer.parseInt(request.getHeader("quizadd")),
                    Integer.parseInt(request.getHeader("fileupload")), Integer.parseInt(request.getHeader("fileedit")));
            JSONObject object = new JSONObject();
            object.put("addnews", success);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value="/{usergroup}", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> editUserGroup(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "usergroup") String usergroup ) {
        JSONObject object = new JSONObject();
        boolean success;
        if (UserGroups.checkValidity(usergroup)) {
            if ((request.getHeader("usergroup")).equals(usergroup)) {
                success = UserGroups.updateUserGroup(request.getHeader("usergroup"),
                        Integer.parseInt(request.getHeader("eventsadd")), Integer.parseInt(request.getHeader("eventsedit")),
                        Integer.parseInt(request.getHeader("eventsdelete")), Integer.parseInt(request.getHeader("eventsview")),
                        Integer.parseInt(request.getHeader("newsadd")), Integer.parseInt(request.getHeader("newsdelete")),
                        Integer.parseInt(request.getHeader("newsedit")), Integer.parseInt(request.getHeader("newsview")),
                        Integer.parseInt(request.getHeader("pagesadd")), Integer.parseInt(request.getHeader("pagesdelete")),
                        Integer.parseInt(request.getHeader("pagesedit")), Integer.parseInt(request.getHeader("pagesview")),
                        Integer.parseInt(request.getHeader("usersadd")), Integer.parseInt(request.getHeader("usersdelete")),
                        Integer.parseInt(request.getHeader("usersedit")), Integer.parseInt(request.getHeader("usersview")),
                        Integer.parseInt(request.getHeader("pointsadd")), Integer.parseInt(request.getHeader("pointsreset")),
                        Integer.parseInt(request.getHeader("pointsview")), Integer.parseInt(request.getHeader("commentsedit")),
                        Integer.parseInt(request.getHeader("quizview")), Integer.parseInt(request.getHeader("quizadd")),
                        Integer.parseInt(request.getHeader("fileupload")), Integer.parseInt(request.getHeader("fileedit")));
                object.put("updatenews", success);
                    return new ResponseEntity<String>(object.toString(), HttpStatus.OK);

            } else
                object.put("updatenews", false);
                return new ResponseEntity<String>(object.toString(), HttpStatus.valueOf(501));
        }
        else {
            success = UserGroups.updateUserGroup(request.getHeader("usergroup"),
                    Integer.parseInt(request.getHeader("eventsadd")), Integer.parseInt(request.getHeader("eventsedit")),
                    Integer.parseInt(request.getHeader("eventsdelete")), Integer.parseInt(request.getHeader("eventsview")),
                    Integer.parseInt(request.getHeader("newsadd")), Integer.parseInt(request.getHeader("newsdelete")),
                    Integer.parseInt(request.getHeader("newsedit")), Integer.parseInt(request.getHeader("newsview")),
                    Integer.parseInt(request.getHeader("pagesadd")), Integer.parseInt(request.getHeader("pagesdelete")),
                    Integer.parseInt(request.getHeader("pagesedit")), Integer.parseInt(request.getHeader("pagesview")),
                    Integer.parseInt(request.getHeader("usersadd")), Integer.parseInt(request.getHeader("usersdelete")),
                    Integer.parseInt(request.getHeader("usersedit")), Integer.parseInt(request.getHeader("usersview")),
                    Integer.parseInt(request.getHeader("pointsadd")), Integer.parseInt(request.getHeader("pointsreset")),
                    Integer.parseInt(request.getHeader("pointsview")), Integer.parseInt(request.getHeader("commentsedit")),
                    Integer.parseInt(request.getHeader("quizview")), Integer.parseInt(request.getHeader("quizadd")),
                    Integer.parseInt(request.getHeader("fileupload")), Integer.parseInt(request.getHeader("fileedit")));
            object.put("updatenews", success);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value="/{usergroup}/delete", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> deleteUserGroup(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "usergroup") String usergroup ){

        boolean success;
            success = UserGroups.deleteUserGroup(usergroup);
            JSONObject object = new JSONObject();
            object.put("deletenews", success);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }
    }



