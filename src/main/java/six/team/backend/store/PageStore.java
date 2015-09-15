package six.team.backend.store;

import java.util.Date;
import java.util.LinkedList;


public class PageStore {
    private String title;
    private String description;
    private Date page_generated;
    private String url;
    private LinkedList content;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getPage_generated() {
        return page_generated;
    }

    public void setPage_generated(Date page_generated) {
        this.page_generated = page_generated;
    }



    public LinkedList getContent() {
        return content;
    }

    public void setContent(LinkedList content) {
        this.content = content;
    }





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public PageStore() {
    }
}
