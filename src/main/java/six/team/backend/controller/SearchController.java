package six.team.backend.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.PageJsonGen;
import six.team.backend.model.News;
import six.team.backend.model.Search;
import six.team.backend.store.EventStore;
import six.team.backend.store.NewsStore;
import six.team.backend.store.PageStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

/**
 * Created by Anna on 23/09/2015.
 */
@Controller
@RequestMapping("/search")
public class SearchController {



    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value="/news", method = RequestMethod.GET)
      public
      @ResponseBody
      ResponseEntity<String> searchNews(HttpServletRequest request, HttpServletResponse response) {

        LinkedList<NewsStore> news = Search.searchNews(request.getHeader("searchtext"));
        JSONObject object = new JSONObject();
        object.put("news", news);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
    }
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value="/events", method = RequestMethod.GET)
      public @ResponseBody ResponseEntity<String> searchEvents(HttpServletRequest request, HttpServletResponse response) {

        LinkedList<EventStore> events = Search.searchEvents(request.getHeader("searchtext"));
        JSONObject object = new JSONObject();
        object.put("events", events);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
    }
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value="/pages", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> searchPages(HttpServletRequest request, HttpServletResponse response) {

        LinkedList<PageStore> pages = Search.searchPages(request.getHeader("searchtext"));
        JSONObject object = new JSONObject();
        object.put("pages", pages);
        return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
    }

}
