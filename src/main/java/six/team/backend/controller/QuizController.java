package six.team.backend.controller;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import six.team.backend.dao.UserDAO;
import six.team.backend.model.Auth;
import six.team.backend.model.Quiz;
import six.team.backend.store.AnswerStore;
import six.team.backend.store.QuestionStore;
import six.team.backend.store.QuizStore;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Gareth on 20/09/2015.
 */
@Controller
@RequestMapping("/quiz")
public class QuizController {
    private final static Logger logger = Logger.getLogger(AuthenticationController.class);

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{quizid}/take", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> getQuiz(@PathVariable(value = "quizid") String id, HttpServletRequest request, HttpServletResponse res) {
        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        if (UD.getUserGroupPermissions(UD.getUserGroup(token), "quizview")) {
            LinkedList<QuestionStore> test = Quiz.getQuiz(id);
            JSONArray array = new JSONArray();
            for (int i = 0; i < test.size(); i++) {
                JSONObject object = new JSONObject();
                JSONArray options = new JSONArray();
                for (int r = -0; r < test.get(i).getAnswers().size(); r++) {
                    options.put(test.get(i).getAnswers().get(r).getAnswer_text());
                }
                object.put("options", options);
                object.put("answer", test.get(i).getCorrectAnswer());
                object.put("question", test.get(i).getQuestion_text());
                array.put(object);
            }
            return new ResponseEntity<String>(array.toString(), HttpStatus.OK);
        } else {
            JSONObject message = new JSONObject();
            message.put("pages", "You are unauthorized to view this quiz");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<String> createQuiz(HttpServletRequest request, HttpServletResponse res) {
        request.getParameter("body");
        JSONArray array = null;
        LinkedList<QuestionStore> questions = new LinkedList<QuestionStore>();
        LinkedList<AnswerStore> answers;
        try {
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String read = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                array = new JSONArray(read);
                for (int i = 0; i < array.length(); i++) {
                    String randomQuestionId = UUID.randomUUID().toString();
                    randomQuestionId = randomQuestionId.replaceAll("-", "");
                    QuestionStore questionStore = new QuestionStore();
                    questionStore.setQuestion_id(randomQuestionId);
                    JSONObject object = array.getJSONObject(i);
                    int answer = object.getInt("answer");
                    JSONArray options = object.getJSONArray("options");
                    String questionText = object.getString("question");
                    questionStore.setQuestion_text(questionText);
                    answers = new LinkedList<AnswerStore>();
                    for (int r = 0; r < options.length(); r++) {
                        AnswerStore answerStore = new AnswerStore();
                        String randomAnswerId = UUID.randomUUID().toString();
                        randomAnswerId = randomAnswerId.replaceAll("-", "");
                        answerStore.setAnswer_id(randomAnswerId);
                        answerStore.setAnswer_text(options.get(r).toString());
                        if (r == answer) {
                            questionStore.setCorrectAnswer(randomAnswerId);
                        }
                        answers.add(answerStore);
                    }
                    questionStore.setAnswers(answers);
                    questions.add(questionStore);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        String quizTitle = request.getHeader("title");
        float passmark = Float.parseFloat(request.getHeader("passmark"));
        int quizPoints = request.getIntHeader("points");
        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        if (UD.getUserGroupPermissions(UD.getUserGroup(token), "quizadd")) {
            Quiz.createQuiz(questions, quizTitle, quizPoints, passmark);
            return new ResponseEntity<String>(array.toString(), HttpStatus.OK);
        } else {
            JSONObject message = new JSONObject();
            message.put("pages", "You are unauthorized to create a quiz");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> getAttr() {
        LinkedList<QuizStore> allQuiz = Quiz.getAllQuizzes();
        JSONObject quizzes = new JSONObject();
        quizzes.put("quiz", allQuiz);
        return new ResponseEntity<String>(quizzes.toString(), HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/complete", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<String> completeQuiz(HttpServletRequest request, HttpServletResponse res) {
        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        int userid = request.getIntHeader("userid");
        String quizid = request.getHeader("quizid");
        float score = Float.parseFloat(request.getHeader("score"));
        if (UD.getUserGroupPermissions(UD.getUserGroup(token), "quizScore")) {
            boolean sucess = Quiz.completeQuiz(userid,quizid,score);
            JSONObject object  = new JSONObject();
            object.put("completed", sucess);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        } else {
            JSONObject message = new JSONObject();
            message.put("pages", "You are unauthorized to update the score on this quiz");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{quizid}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> getQuizInfo(@PathVariable(value = "quizid") String id, HttpServletRequest request, HttpServletResponse res) {
        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        if (UD.getUserGroupPermissions(UD.getUserGroup(token), "quizview")) {
                QuizStore quiz  = new QuizStore();
                quiz = Quiz.getQuizInfo(id);
                JSONObject object = new JSONObject();
                object.put("title", quiz.getQuiz_title());
                object.put("id", quiz.getQuiz_id());
                object.put("passmark", quiz.getPassmark());
                object.put("points", quiz.getPoints());
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        } else {
            JSONObject message = new JSONObject();
            message.put("pages", "You are unauthorized to view this quiz");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }


}