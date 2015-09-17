package six.team.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.PageJsonGen;
import six.team.backend.model.News;
import six.team.backend.model.User;
import six.team.backend.store.NewsStore;
import six.team.backend.store.PageStore;
import six.team.backend.store.UserStore;

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
    public @ResponseBody void addNews() {
        LinkedList<NewsStore> news = News.getAll();
        PageJsonGen pageJsonGen = new PageJsonGen();
        //Send values to the page json generator, this will return the full json which is sent to the client
        //Information about the page may be needed to be collected from the db, this is for discussion
        //return pageJsonGen.createPageJson("Users", "A list of all registered users", news);

    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody getNews() {
        NewsStore news = News.get();
        PageJsonGen pageJsonGen = new PageJsonGen();
        //Send values to the page json generator, this will return the full json which is sent to the client
        //Information about the page may be needed to be collected from the db, this is for discussion
        return pageJsonGen.createPageJson("Users", "A list of all registered users", news);

    }
    @RequestMapping(value ="/{id}",method = RequestMethod.POST)
    public @ResponseBody PageStore printUsers() {
        PageJsonGen pageJsonGen = new PageJsonGen();
        //Send values to the page json generator, this will return the full json which is sent to the client
        //Information about the page may be needed to be collected from the db, this is for discussion
      //  return pageJsonGen.createPageJson("Users", "A list of all registered users", news);
    }
}
