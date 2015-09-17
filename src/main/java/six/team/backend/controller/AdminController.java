package six.team.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.PageJsonGen;
import six.team.backend.dao.UserDAO;
import six.team.backend.model.User;
import six.team.backend.store.PageStore;
import six.team.backend.store.UserStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

/**
 * Created by Anna on 16/09/2015.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(method=RequestMethod.GET, value={"/users"})
        public @ResponseBody PageStore getUnauthorised() {
        LinkedList<UserStore> users = User.getAllUnauthorised();
        PageJsonGen pageJsonGen = new PageJsonGen();
        //Send values to the page json generator, this will return the full json which is sent to the client
        //Information about the page may be needed to be collected from the db, this is for discussion
        return pageJsonGen.createPageJson("Users", "A list of all unauthorized users", users);

    }
    @RequestMapping(method=RequestMethod.POST, value={"/users"})
    public @ResponseBody boolean approveUser(HttpServletRequest request,HttpServletResponse res) {
        String approved_id = request.getHeader("approvedId");
        String approved_group = request.getHeader("approvedGroup");
        String approved_status= request.getHeader("approvedStatus");
        User user = new User();
        if(approved_status.equals("approved")){
            user.approve(Integer.parseInt(approved_id),approved_group);
        }  else{

            user.delete(Integer.parseInt(approved_id));}
        //Send values to the page json generator, this will return the full json which is sent to the client
        //Information about the page may be needed to be collected from the db, this is for discussion
        return true;
    }
}
