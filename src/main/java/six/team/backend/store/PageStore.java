package six.team.backend.store;

import java.util.Date;
import java.util.LinkedList;


public class PageStore {
    private int pageid;
    private String title;
    private String description;
    private String text;
    private String slug;
    private String permission;
    private LinkedList<PageStore> subpages;
    private String parentSlug;
    private int order;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {this.description = description;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

    public String getSlug() {return slug;}

    public void setSlug(String slug) {this.slug = slug;}

    public String getPermission() {return permission;}

    public void setPermission(String permission) {this.permission = permission;}

    public LinkedList<PageStore> getSubpages() {return subpages;}

    public void setSubpages(LinkedList<PageStore> subpages) {this.subpages = subpages;}

    public String getParentSlug() {return parentSlug;}

    public void setParentSlug(String parentSlug) {this.parentSlug = parentSlug;}

    public int getOrder() {return order;}

    public void setOrder(int order) {this.order = order;}

    public int getPageid() {return pageid;}

    public void setPageid(int pageid) {this.pageid = pageid;}
}
