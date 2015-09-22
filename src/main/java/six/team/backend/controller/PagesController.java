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
    public@ResponseBody ResponseEntity<String> addPage(HttpServletRequest request, HttpServletResponse response) {
       boolean exists= Pages.checkValidity(request.getHeader("title"));
       if(exists){
           return new ResponseEntity<String>("The title already exists", HttpStatus.valueOf(409));
       }
       else{
           boolean success=Pages.addPage(request.getHeader("parentSlug"),request.getHeader("title"),request.getHeader("description"),request.getHeader("text"),request.getHeader("permission"),Integer.parseInt(request.getHeader("order")));
           if(success)
               return new ResponseEntity<String>("Page added", HttpStatus.valueOf(201));
           else
               return new ResponseEntity<String>("Failed to add page", HttpStatus.valueOf(201));
       }

    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/{slug}", method = RequestMethod.DELETE)
     public@ResponseBody ResponseEntity<String> deletePage(HttpServletRequest request, HttpServletResponse response, @PathVariable(value="slug") String slug) {
        boolean success=Pages.deletePage(slug);
        if(success)
            return new ResponseEntity<String>("Page deleted", HttpStatus.valueOf(201));
        else
            return new ResponseEntity<String>("Page couldn be deleted", HttpStatus.valueOf(201));
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/{slug}", method = RequestMethod.POST)
    public@ResponseBody ResponseEntity<String> editPage(HttpServletRequest request, HttpServletResponse response,@PathVariable(value="slug") String slug) {
            boolean success=Pages.editPage(slug,request.getHeader("parentSlug"), request.getHeader("title"), request.getHeader("description"), request.getHeader("text"), request.getHeader("permission"), Integer.parseInt(request.getHeader("order")));
            if(success)
                return new ResponseEntity<String>("Page edited", HttpStatus.valueOf(201));
            else
                return new ResponseEntity<String>("Page couldn be edited", HttpStatus.valueOf(201));

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
