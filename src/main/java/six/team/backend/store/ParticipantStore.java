package six.team.backend.store;

/**
 * Created by Gareth on 19/09/2015.
 */
public class ParticipantStore {
    int event_id;
    int userid;
    int attended;

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }

}
