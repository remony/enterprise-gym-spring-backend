package six.team.backend.store;

/**
 * Created by Gareth on 28/09/2015.
 */
public class AttemptStore {
    String quiz_title;
    int no_of_attempts;
    int score;
    int completed;

    public String getQuiz_title() {
        return quiz_title;
    }

    public void setQuiz_title(String quiz_title) {
        this.quiz_title = quiz_title;
    }
    public int getNo_of_attempts() {
        return no_of_attempts;
    }

    public void setNo_of_attempts(int no_of_attempts) {
        this.no_of_attempts = no_of_attempts;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }




}
