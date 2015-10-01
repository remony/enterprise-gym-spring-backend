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
    //this endpoint returns a list of all the news
    ResponseEntity<String> printNews(HttpServletRequest request, HttpServletResponse response) {

        LinkedList<NewsStore> news = News.getAll(Integer.parseInt(request.getHeader("page")), Integer.parseInt(request.getHeader("pagesize")));
        JSONObject object = new JSONObject();
        int count =News.getPagesNumber();
        object.put("allnews", news);
        object.put("count", (count + Integer.parseInt(request.getHeader("pagesize")) - 1) /Integer.parseInt(request.getHeader("pagesize")));
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
    }


    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    //this endpoint creates news from the headers given, the text used is passed in as raw data in the request body
    public @ResponseBody ResponseEntity<String> addNews(HttpServletRequest request,HttpServletResponse response, @RequestBody String text) {
        boolean success,exists;
        JSONObject object = new JSONObject();

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");

        if(UD.getUserGroupPermissions(UD.getUserGroup(token),"newsadd")){
            exists = News.checkValidity(request.getHeader("title"));
            if(exists)
            {
                object.put("message","The title already exists");
                return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
            }
            else {
                success = News.save(request.getHeader("title"), text , request.getHeader("permission"));
                if (success) {
                    object.put("message", "The news was added successfully");
                    return new ResponseEntity<String>(object.toString(), HttpStatus.valueOf(201));
                }
                else{
                    object.put("message", "The news was not added successfully");
                    return new ResponseEntity<String>(object.toString(), HttpStatus.valueOf(401));
                }
            }

        }else {
            JSONObject message = new JSONObject();
            message.put("user", "You are Unauthorized to view this content");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }



    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/{slug}", method = RequestMethod.GET)
    //this endpoint returns the details of the slug given in the url
    public @ResponseBody ResponseEntity<String> getNews(@PathVariable(value="slug") String slug) {
        LinkedList<NewsStore> news = News.get(slug);
        if(news==null){
            return new ResponseEntity<String>("There is no news with that id", HttpStatus.NOT_FOUND);
        }
        JSONObject object = new JSONObject();
        object.put("article", news);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);


    }



    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value ="/{slug}",method = RequestMethod.POST)
    //this endpoint is used to update the news with the slug given in the url
    public @ResponseBody ResponseEntity<String> updateNews(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "slug") String slug , @RequestBody String text){

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");

        if(UD.getUserGroupPermissions(UD.getUserGroup(token), "newsedit")){

            boolean success,exists;
            JSONObject message = new JSONObject();
            if(News.checkValidity(request.getHeader("title"))){
             //   if (News.generateSlug(request.getHeader("title")).equals(slug)) {
                    success=News.update(slug, request.getHeader("title"), text, request.getHeader("permission"));
                    if(success) {
                        message.put("status", "success");
                        message.put("slug", slug);
                        return new ResponseEntity<String>(message.toString(), HttpStatus.OK);
                    } else {
                        message.put("status", "success");
                        return new ResponseEntity<String>(message.toString(), HttpStatus.valueOf(501));
                    }

             //   } else
               //     return new ResponseEntity<String>(message.toString(), HttpStatus.valueOf(501));
            }else
            {
                success = News.update(slug, request.getHeader("title"), request.getHeader("text"), request.getHeader("permission"));
                if(success) {
                    message.put("status", success);
                    message.put("slug", slug);
                    return new ResponseEntity<String>(message.toString(), HttpStatus.OK);
                } else {
                    message.put("status", success);
                    return new ResponseEntity<String>(message.toString(), HttpStatus.valueOf(501));
                }
            }
        }else {
            JSONObject message = new JSONObject();
            message.put("user", "You are Unauthorized to view this content");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }


    }


    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value ="/{slug}/delete",method = RequestMethod.POST)
    //this endpoint deletes the news with the slug given in the url
    public @ResponseBody ResponseEntity deleteNews(HttpServletRequest request, @PathVariable(value = "slug") String slug ){

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");

        if(UD.getUserGroupPermissions(UD.getUserGroup(token),"newsdelete")){

            boolean success=News.delete(slug);
            JSONObject object = new JSONObject();
            object.put("message","news deleted: " + success);
            if(success)
                return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
            else
                return new ResponseEntity<String>(object.toString(), HttpStatus.valueOf(501));

        }else {
            JSONObject message = new JSONObject();
            message.put("user", "You are Unauthorized to view this content");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "{slug}/comments", method = RequestMethod.GET)
    //this endpoint gets all the comments for the slug given in the url
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
    //this endpoint adds comments to the slug given in the url
    @ResponseBody
    ResponseEntity addComments(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "slug") String slug) {

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");

        if(UD.userCheck(UD.getUserName(token))){
            boolean success = News.addComment(slug, request.getHeader("text"), request.getHeader("author"));
            JSONObject object = new JSONObject();
            object.put("message",success);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        }else {
            JSONObject message = new JSONObject();
            message.put("user", "You are Unauthorized to view this content");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }


    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{slug}/comment/{commentid}/delete", method = RequestMethod.DELETE)
    public
    //this endpoint deletes the comments with comment id given in the url for the slug given in the url
    @ResponseBody
    ResponseEntity deleteComment(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "commentid") String commentid, @PathVariable(value = "slug") String slug) {

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        String author = request.getHeader("author");

        if(UD.getUserGroupPermissions(UD.getUserGroup(token),"commentsedit") || author.equals(UD.getUserName(token))){
            boolean success = News.deleteComment(Integer.parseInt(commentid));
            JSONObject object = new JSONObject();
            object.put("message",success);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);

        }else {
            JSONObject message = new JSONObject();
            message.put("user", "You are Unauthorized to view this content");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{slug}/comment/{commentid}", method = RequestMethod.POST)
    public
    //this endpoint edits the comment with comment id given in the url for the slug given in the url
    @ResponseBody ResponseEntity editComment(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "commentid") String commentid, @PathVariable(value = "slug") String slug) {

        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        String author = request.getHeader("author");

        if (UD.getUserGroupPermissions(UD.getUserGroup(token), "commentsedit") || author.equals(UD.getUserName(token))) {

            boolean success = News.editComment(Integer.parseInt(commentid), request.getHeader("text"));
            JSONObject object = new JSONObject();
            object.put("message", success);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);

        } else {
            JSONObject message = new JSONObject();
            message.put("user", "You are Unauthorized to view this content");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

}
