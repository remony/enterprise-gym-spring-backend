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
import six.team.backend.store.AttemptStore;
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

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{quizid}/take", method = RequestMethod.GET)
    public
    @ResponseBody
    //this endpoint returns json in the correct format for the quiz given in the url, it returns details of the passmark and the points given for the quiz completed
    ResponseEntity<String> getQuiz(@PathVariable(value = "quizid") String id, HttpServletRequest request, HttpServletResponse res) {
        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        if (UD.getUserGroupPermissions(UD.getUserGroup(token), "quizview")) {
            LinkedList<QuestionStore> test = Quiz.getQuiz(id);
            QuizStore quizInfo = Quiz.getQuizInfo(id);
            JSONArray array = new JSONArray();
            JSONObject quiz = new JSONObject();
            quiz.put("title", quizInfo.getQuiz_title());
            quiz.put("passmark", quizInfo.getPassmark());
            quiz.put("points", quizInfo.getPoints());
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
            quiz.put("questions", array);
            return new ResponseEntity<String>(quiz.toString(), HttpStatus.OK);
        } else {
            JSONObject message = new JSONObject();
            message.put("pages", "You are unauthorized to view this quiz");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/create", method = RequestMethod.POST)
    public
    //this endpoint creates the quiz , reads in the json passed in the body of the post request and formats into a json to create the quiz
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
                        answerStore.setAnswer_text(options.getJSONObject(r).getString("title"));
                        System.out.println(answerStore.getAnswer_text());
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
    //this endpoint returns a list of all available quizes
    ResponseEntity<String> getAllQuizzes() {
        LinkedList<QuizStore> allQuiz = Quiz.getAllQuizzes();
        JSONObject quizzes = new JSONObject();
        quizzes.put("quiz", allQuiz);
        return new ResponseEntity<String>(quizzes.toString(), HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "{quizid}/complete", method = RequestMethod.POST)
    public
    @ResponseBody
    //this endpoint compeltes a quiz and save the score and whether they have passed
    ResponseEntity<String> completeQuiz(@PathVariable(value = "quizid") String id,HttpServletRequest request, HttpServletResponse res) {
        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        int userid = UD.getUserID(token);
        float score = Float.parseFloat(request.getHeader("score"));
        if (UD.getUserGroupPermissions(UD.getUserGroup(token), "quizScore")) {
            boolean sucess = Quiz.completeQuiz(userid,id,score);
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
    //this endpoint returns the info for the id given in the url
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

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/users/{userid}", method = RequestMethod.GET)
    public
    //this endpoint returns the quizzes that the users have taken
    @ResponseBody
    ResponseEntity<String> getUserQuizzes(@PathVariable(value = "userid") String id, HttpServletRequest request, HttpServletResponse res) {
        UserDAO UD = new UserDAO();
        String token = request.getHeader("token");
        if (UD.getUserGroupPermissions(UD.getUserGroup(token), "quizview")) {
            LinkedList<AttemptStore> attempts = new LinkedList<AttemptStore>();
            attempts = Quiz.getAttemptInfo(Integer.parseInt(id));
            JSONObject object = new JSONObject();
            object.put("attempts", attempts);
            return new ResponseEntity<String>(object.toString(), HttpStatus.OK);
        } else {
            JSONObject message = new JSONObject();
            message.put("attempts", "You are unauthorized to view this quiz");
            return new ResponseEntity<String>(message.toString(), HttpStatus.UNAUTHORIZED);
        }
    }


}