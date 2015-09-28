package six.team.backend.model;


import six.team.backend.dao.QuizDAO;
import six.team.backend.store.AnswerStore;
import six.team.backend.store.QuestionStore;
import six.team.backend.store.QuizStore;

import java.util.LinkedList;

/**
 * Created by Gareth on 20/09/2015.
 */
public class Quiz {
    public static   LinkedList<QuestionStore> getQuiz(String quizId){
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.getQuiz(quizId);
    }

    public static   LinkedList<QuestionStore> createQuiz(LinkedList<QuestionStore> quiz, String quizTitle, int points, float passmark){
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.createQuiz(quiz, quizTitle, points, passmark);
    }

    public static LinkedList<QuizStore> getAllQuizzes(){
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.getAllQuizzes();
    }

    public static boolean completeQuiz(int userid, String quizid, float score){
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.completeQuiz(userid, quizid, score);
    }

    public static QuizStore getQuizInfo(String quizid){
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.getQuizInfo(quizid);
    }


}
