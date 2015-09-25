package six.team.backend.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import six.team.backend.PageJsonGen;
import six.team.backend.dao.UserDAO;
import six.team.backend.model.News;
import six.team.backend.model.Pages;
import six.team.backend.store.CommentStore;
import six.team.backend.store.NewsStore;
import six.team.backend.store.PageStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

/**
 * Created by Anna on 20/09/2015.
 */
@Controller
@RequestMapping("/pages")
public class PagesController {

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public@ResponseBody ResponseEntity<String> addPage(HttpServletRequest request, HttpServletResponse response, @RequestBody String text) {
        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        if(UD.getUserGroupPermissions(UD.getUserGroup(token),"pageadd")) {
            boolean exists = Pages.checkValidity(request.getHeader("title"));
            JSONObject json = new JSONObject();
            json.put("exists", exists);

            if (exists) {
                return new ResponseEntity<String>(json.toString(), HttpStatus.valueOf(409));
            } else {
                boolean success = Pages.addPage(request.getHeader("parentSlug"), request.getHeader("title"), request.getHeader("description"), text, request.getHeader("permission"), Integer.parseInt(request.getHeader("order")));
                json.put("sucess", success);
                if (success)
                    return new ResponseEntity<String>(json.toString(), HttpStatus.valueOf(201));
                else
                    return new ResponseEntity<String>(json.toString(), HttpStatus.valueOf(201));
            }
        }else {
            JSONObject message = new JSONObject();
            message.put("pages", "You are unauthorized to add a page");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }

    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/{slug}/delete", method = RequestMethod.POST)
    public@ResponseBody ResponseEntity<String> deletePage(HttpServletRequest request, HttpServletResponse response, @PathVariable(value="slug") String slug) {
        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        if(UD.getUserGroupPermissions(UD.getUserGroup(token),"pagesdelete")) {
            boolean success = Pages.deletePage(slug);
            JSONObject json = new JSONObject();
            json.put("sucess", success);
            if (success) {
                return new ResponseEntity<String>(json.toString(), HttpStatus.valueOf(201));
            } else {
                return new ResponseEntity<String>(json.toString(), HttpStatus.valueOf(201));
            }
        }else {
            JSONObject message = new JSONObject();
            message.put("pages", "You are unauthorized to delete a page");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/{slug}/edit", method = RequestMethod.POST)
    public@ResponseBody ResponseEntity<String> editPage(HttpServletRequest request, HttpServletResponse response,@PathVariable(value="slug") String slug, @RequestBody String text) {
        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        if(UD.getUserGroupPermissions(UD.getUserGroup(token),"pagesedit")) {
            boolean success = Pages.editPage(slug, request.getHeader("parentSlug"), request.getHeader("title"), request.getHeader("description"), text, request.getHeader("permission"), Integer.parseInt(request.getHeader("order")));
            JSONObject json = new JSONObject();
            json.put("sucess", success);
            if (success) {
                return new ResponseEntity<String>(json.toString(), HttpStatus.valueOf(201));
            } else {
                return new ResponseEntity<String>(json.toString(), HttpStatus.valueOf(201));
            }
        }else {
            JSONObject message = new JSONObject();
            message.put("pages", "You are unauthorized to edit a page");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public@ResponseBody ResponseEntity<String> getAll() {
        LinkedList<PageStore> pages = Pages.getAll();
        JSONObject object = new JSONObject();
        object.put("pages", pages);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/{slug}", method = RequestMethod.GET)
    public@ResponseBody ResponseEntity<String> get(@PathVariable(value="slug") String slug) {
        LinkedList<PageStore> page = Pages.get(slug);
        JSONObject object = new JSONObject();
        object.put("singlepage", page);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);

    }
}