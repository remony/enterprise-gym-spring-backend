package six.team.backend.store;

import java.util.LinkedList;

/**
 * Created by Gareth on 20/09/2015.
 */
public class QuestionStore {
    int question_id;
    String question_text;
    int correctAnswer;
    LinkedList<AnswerStore> answers;

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
    public LinkedList<AnswerStore> getAnswers() {
        return answers;
    }

    public void setAnswers(LinkedList<AnswerStore> answers) {
        this.answers = answers;
    }

}
