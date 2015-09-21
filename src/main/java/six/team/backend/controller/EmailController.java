package six.team.backend.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.utils.Email;

import javax.servlet.http.HttpServletRequest;
/*

            This controller and endpoints are for test purposes only and should be removed in production

 */
@Controller
@RequestMapping("/")
public class EmailController {

    @RequestMapping(
            method = RequestMethod.POST,
            value ="/email")
    public @ResponseBody
    ResponseEntity<String> filesImageError(HttpServletRequest req) {
        String receipt = req.getHeader("receipt");
        String subject = req.getHeader("subject");
        String content = req.getHeader("content");
        Email email = new Email();

        if (email.sendEmail(receipt, subject, content)) {
            return new ResponseEntity<String>("Email sent", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Email Failed", HttpStatus.OK);
        }
    }

}
