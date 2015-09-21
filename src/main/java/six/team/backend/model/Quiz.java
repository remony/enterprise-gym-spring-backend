package six.team.backend.model;


import six.team.backend.dao.QuizDAO;
import six.team.backend.store.AnswerStore;
import six.team.backend.store.QuestionStore;

import java.util.LinkedList;

/**
 * Created by Gareth on 20/09/2015.
 */
public class Quiz {
    public static   LinkedList<QuestionStore> getQuiz(int quizId){
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.getQuiz(quizId);

    }
}
