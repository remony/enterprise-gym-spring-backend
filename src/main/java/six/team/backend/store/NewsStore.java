package six.team.backend.store;

import java.util.Date;

/**
 * Created by Anna on 17/09/2015.
 */
public class NewsStore {
    private int id;
    private String title;
    private String text;
    private Date date;
    private String time;
    private CommentStore comments;
    private String lock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CommentStore getComments() {
        return comments;
    }

    public void setComments(CommentStore comments) {
        this.comments = comments;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }
}
