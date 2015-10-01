package six.team.backend.store;

/**
 * Created by admin on 22/09/2015.
 */
public class QuizStore {
    private String quiz_id;
    private String quiz_title;
    private int points;
    private float passmark;

    public String getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getQuiz_title() {
        return quiz_title;
    }

    public void setQuiz_title(String quiz_title) {
        this.quiz_title = quiz_title;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public float getPassmark() {
        return passmark;
    }

    public void setPassmark(float passmark) {
        this.passmark = passmark;
    }
}
