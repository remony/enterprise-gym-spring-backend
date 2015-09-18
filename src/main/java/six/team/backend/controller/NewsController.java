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
import six.team.backend.store.CommentStore;
import six.team.backend.store.NewsStore;
import six.team.backend.store.PageStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

/**
 * Created by Anna on 17/09/2015.
 */
@Controller
@RequestMapping("/news")
public class NewsController {


    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> printNews() {
        LinkedList<NewsStore> news = News.getAll();
        PageJsonGen pageJsonGen = new PageJsonGen();
        JSONObject object = new JSONObject();
        object.put("allnews", news);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
    }

    @

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<String> addNews(HttpServletRequest request, HttpServletResponse response) {
        boolean success;
        success = News.save(request.getHeader("title"), request.getHeader("text"), request.getHeader("permission"));
        if (success)
            return new ResponseEntity<String>("", HttpStatus.valueOf(201));
        else
            return new ResponseEntity<String>("", HttpStatus.valueOf(401));

    }


    @

    @RequestMapping(value = "/{slug}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> getNews(@PathVariable(value="slug") String slug) {
        NewsStore news = News.get(slug);
        if(news==null){
            return new ResponseEntity<String>("There is no news with that id", HttpStatus.NOT_FOUND);
        }
        JSONObject object = new JSONObject();
        object.put("article", news);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);


    }



    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{slug}", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<String> updateUser(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "slug") String slug) {
        boolean success = News.update(slug, request.getHeader("title"), request.getHeader("text"), request.getHeader("permission"));
        if(success)
            return new ResponseEntity<String>("The news was edited succesfully", HttpStatus.OK);
        else
            return new ResponseEntity<String>("News Cant be edited", HttpStatus.valueOf(501));
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{slug}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity deleteUser(@PathVariable(value = "slug") String slug) {
        boolean success = News.delete(slug);
        if(success)
            return new ResponseEntity<String>("The news was deleted succesfully", HttpStatus.OK);
        else
            return new ResponseEntity<String>("News Cant be deleted", HttpStatus.valueOf(501));
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "{slug}/comments", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity viewComments(@PathVariable(value = "slug") String slug) {

        LinkedList<CommentStore> comments = News.getAllComments(slug);
        JSONObject object = new JSONObject();
        object.put("newscomments", comments);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);

    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{slug}/comments", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity addComments(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "slug") String slug) {
        boolean success = News.addComment(slug, request.getHeader("text"), request.getHeader("author"));
        return new ResponseEntity<String>("The news was deleted succesfully", HttpStatus.OK);

    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{slug}/comment/{commentid}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity deleteComment(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "commentid") String commentid, @PathVariable(value = "slug") String slug) {
        boolean success = News.deleteComment(Integer.parseInt(commentid));
        return new ResponseEntity<String>("The news was deleted succesfully", HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{slug}/comment/{commentid}", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity editComment(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "commentid") String commentid, @PathVariable(value = "slug") String slug) {
        boolean success = News.editComment(Integer.parseInt(commentid), request.getHeader("text"));
        return new ResponseEntity<String>("The news was deleted succesfully", HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    PageStore addNews() {
        LinkedList<NewsStore> news = News.getAll();
        PageJsonGen pageJsonGen = new PageJsonGen();
        //Send values to the page json generator, this will return the full json which is sent to the client
        //Information about the page may be needed to be collected from the db, this is for discussion
        return pageJsonGen.createPageJson("Users", "A list of all registered users", news);

    }

}
