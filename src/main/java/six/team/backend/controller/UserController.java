package six.team.backend.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.PageJsonGen;
import six.team.backend.model.User;
import six.team.backend.store.PageStore;
import six.team.backend.store.UserStore;

import java.util.LinkedList;


@Controller
@RequestMapping("/users")
public class UserController {
    private final static Logger logger = Logger.getLogger(UserController.class);

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody PageStore printUsers() {
        LinkedList<UserStore> users = User.getAll();

        PageJsonGen pageJsonGen = new PageJsonGen();
        //Send values to the page json generator, this will return the full json which is sent to the client

        //Information about the page may be needed to be collected from the db, this is for discussion
        return pageJsonGen.createPageJson("Users", "A list of all registered users", users);

    }

}