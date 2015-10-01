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
import six.team.backend.model.Pages;
import six.team.backend.store.PageStore;

import java.util.LinkedList;

@Controller
@RequestMapping("/navigation")
public class PagesNavigationController {
    //this endpoint returns the hierchy for the naviagation
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public@ResponseBody
    ResponseEntity<String> getHierarchy() {
        return new ResponseEntity<String>(Pages.getHierarchy().toString(), HttpStatus.OK);

    }
}
