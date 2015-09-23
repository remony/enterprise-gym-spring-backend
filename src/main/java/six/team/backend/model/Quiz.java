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

    public static   LinkedList<QuestionStore> createQuiz(LinkedList<QuestionStore> quiz, String quizTitle){
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.createQuiz(quiz, quizTitle);
    }

    public static LinkedList<QuizStore> getAllQuizzes(){
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.getAllQuizzes();
    }
}
