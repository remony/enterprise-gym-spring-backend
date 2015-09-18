package six.team.backend.store;

import java.util.Date;
<<<<<<< HEAD
import java.util.LinkedList;


=======

/**
 * Created by Anna on 17/09/2015.
 */
>>>>>>> Still have things to change but news store and comment store added some changes to news dao and news controller
public class NewsStore {
    private int id;
    private String title;
    private String slug;
    private String text;

    private Date datecreated;
    private Date lastedited;
    private LinkedList<CommentStore> comments;
    private String permission;
    private String date;
    private String time;
    private String lock;

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

    public void setText(String text) {this.text = text;}

    public Date getDateCreated() { return datecreated; }

    public void setDateCreated(Date datecreated) { this.datecreated = datecreated; }

    public LinkedList<CommentStore> getComments() { return comments;}

    public void setComments(LinkedList<CommentStore> comments) {this.comments = comments;}

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getSlug() {return slug;}

    public void setSlug(String slug) {this.slug = slug;}

    public Date getLastedited() {return lastedited;}

    public void setLastedited(Date lastedited) {this.lastedited = lastedited;}


    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

}
