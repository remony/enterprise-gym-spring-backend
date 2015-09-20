package six.team.backend.store;

import java.util.Date;

/**
 * Created by Anna on 17/09/2015.
 */
public class CommentStore {
    private int commentid;
    private String author;
    private String text;
    private java.util.Date date;
    private String slug;

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public String getSlug() {return slug;}

    public void setSlug(String slug) {this.slug = slug;}
}
