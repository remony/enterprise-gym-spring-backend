package six.team.backend.controller;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import six.team.backend.model.Auth;
import six.team.backend.model.Quiz;
import six.team.backend.store.AnswerStore;
import six.team.backend.store.QuestionStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

/**
 * Created by Gareth on 20/09/2015.
 */
@Controller
@RequestMapping("/quiz")
public class QuizController {
    private final static Logger logger = Logger.getLogger(AuthenticationController.class);

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/{quizid}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> getAttr(@PathVariable(value="quizid") String id ) {
        LinkedList<QuestionStore> test = Quiz.getQuiz(Integer.parseInt(id));
        JSONArray array = new JSONArray();
        for(int i=0; i<test.size(); i++){
            JSONObject object = new JSONObject();
           // object.put("Question", test.get(i).getQuestion_text());
            JSONArray options = new JSONArray();
           for(int r=-0; r<test.get(i).getAnswers().size(); r++){
              options.put(test.get(i).getAnswers().get(r).getAnswer_text());
           }
            JSONObject options1 = new JSONObject();
            object.put("options", options);

            JSONObject correct = new JSONObject();
            object.put("answer", test.get(i).getCorrectAnswer());
            object.put("question", test.get(i).getQuestion_text());

            array.put(object);
        }
        return new ResponseEntity<String>(array.toString(), HttpStatus.OK);
    }
}