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
    public @ResponseBody PageStore printNews() {
        LinkedList<NewsStore> news = News.getAll();
        PageJsonGen pageJsonGen = new PageJsonGen();
        //Send values to the page json generator, this will return the full json which is sent to the client
        //Information about the page may be needed to be collected from the db, this is for discussion
        return pageJsonGen.createPageJson("Users", "A list of all registered users", news);

    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody PageStore addNews() {
        LinkedList<NewsStore> news = News.getAll();
        PageJsonGen pageJsonGen = new PageJsonGen();
        //Send values to the page json generator, this will return the full json which is sent to the client
        //Information about the page may be needed to be collected from the db, this is for discussion
        return pageJsonGen.createPageJson("Users", "A list of all registered users", news);

    }

    @RequestMapping(method = RequestMethod.GET, value={"{/id}"})
    public @ResponseBody ResponseEntity<String> getNews(@PathVariable(value="id") String id) {
        NewsStore news = News.get(Integer.parseInt(id));
        if(news==null){
            return new ResponseEntity<String>("There is no news with that id", HttpStatus.NOT_FOUND);
        }
        PageJsonGen pageJsonGen = new PageJsonGen();
        JSONObject object = new JSONObject();
        object.put("newsid", news);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);


    }
    @RequestMapping(value ="/{id}",method = RequestMethod.POST)
    public @ResponseBody void updateUser(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "id") String id ){
        News.update(Integer.parseInt(id),request.getHeader("title"),request.getHeader("text"));
        PageJsonGen pageJsonGen = new PageJsonGen();
        //Send values to the page json generator, this will return the full json which is sent to the client
        //Information about the page may be needed to be collected from the db, this is for discussion
      // return pageJsonGen.createPageJson("Users", "A list of all registered users", news);
    }
    @RequestMapping(value ="/{id}",method = RequestMethod.DELETE)
    public @ResponseBody void updateUser(@PathVariable(value = "id") String id ){
        News.delete(Integer.parseInt(id));
        PageJsonGen pageJsonGen = new PageJsonGen();
        //Send values to the page json generator, this will return the full json which is sent to the client
        //Information about the page may be needed to be collected from the db, this is for discussion
        // return pageJsonGen.createPageJson("Users", "A list of all registered users", news);
    }
}
