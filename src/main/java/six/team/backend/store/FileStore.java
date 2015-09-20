package six.team.backend.store;

import java.sql.Date;

public class FileStore {

    private int id;
    private String filename;
    private String expanded_filename;
    private int event_id;
    private int news_id;
    private int page_id;

    /*
        Method: addFileStore
        Description: A simplified method for quicking importing of entities when inserting all values
     */
    public void addFileStore(int id, String filename, String expanded_filename, int event_id, int news_id, int page_id, Date date_uploaded) {
        this.id = id;
        this.filename = filename;
        this.expanded_filename = expanded_filename;
        this.event_id = event_id;
        this.news_id = news_id;
        this.page_id = page_id;
        this.date_uploaded = date_uploaded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExpanded_filename() {
        return expanded_filename;
    }

    public void setExpanded_filename(String file_location) {
        this.expanded_filename = file_location;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public int getPage_id() {
        return page_id;
    }

    public void setPage_id(int page_id) {
        this.page_id = page_id;
    }

    public Date getDate_uploaded() {
        return date_uploaded;
    }

    public void setDate_uploaded(Date date_uploaded) {
        this.date_uploaded = date_uploaded;
    }

    private Date date_uploaded;

}
