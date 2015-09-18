package six.team.backend.store;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Anna on 17/09/2015.
 */
public class NewsStore {
    private int id;
    private String title;
    private String slug;
    private String text;
    private Date datecreated;
    private Date lastedited;
    private LinkedList<CommentStore> comments;
    private String permission;



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
}
