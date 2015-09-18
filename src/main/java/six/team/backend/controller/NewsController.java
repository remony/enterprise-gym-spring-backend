package six.team.backend.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.PageJsonGen;
import six.team.backend.model.News;
import six.team.backend.model.User;
import six.team.backend.store.NewsStore;
import six.team.backend.store.PageStore;
import six.team.backend.store.UserStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

/**
 * Created by Anna on 17/09/2015.
 */
@Controller
@RequestMapping("/news")
public class NewsController {

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> printNews() {
        LinkedList<NewsStore> news = News.getAll();
        PageJsonGen pageJsonGen = new PageJsonGen();
        JSONObject object = new JSONObject();
        object.put("allnews", news);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);


    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> addNews(HttpServletRequest request,HttpServletResponse response) {
        boolean success;
        success=News.save(request.getHeader("title"),request.getHeader("text"),request.getHeader("permission"));
        if(success)
         return new ResponseEntity<String>("", HttpStatus.valueOf(201));
        else
            return new ResponseEntity<String>("", HttpStatus.valueOf(401));

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> getNews(@PathVariable(value="id") String id) {
        NewsStore news = News.get(Integer.parseInt(id));
        if(news==null){
            return new ResponseEntity<String>("There is no news with that id", HttpStatus.NOT_FOUND);
        }
        JSONObject object = new JSONObject();
        object.put("newsid", news);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);


    }

    @RequestMapping(value ="/{id}",method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> updateUser(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "id") String id ){
        boolean success=News.update(Integer.parseInt(id),request.getHeader("title"),request.getHeader("text"),request.getHeader("permission"));
        if(success)
            return new ResponseEntity<String>("The news was edited succesfully", HttpStatus.OK);
        else
            return new ResponseEntity<String>("News Cant be edited", HttpStatus.valueOf(501));
    }

    @RequestMapping(value ="/{id}",method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity updateUser(@PathVariable(value = "id") String id ){
        boolean success=News.delete(Integer.parseInt(id));
        if(success)
            return new ResponseEntity<String>("The news was deleted succesfully", HttpStatus.OK);
        else
            return new ResponseEntity<String>("News Cant be deleted", HttpStatus.valueOf(501));
    }
}
