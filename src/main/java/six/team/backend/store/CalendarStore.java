package six.team.backend.store;

/**
 * Created by Gareth on 23/09/2015.
 */
public class CalendarStore {

    String url;
    String title;
    String start;
    String end;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartdate() {
        return start;
    }

    public void setStartdate(String start) {
        this.start = start;
    }

    public String getEnddate() {
        return end;
    }

    public void setEnddate(String end) {
        this.end = end;
    }


}
