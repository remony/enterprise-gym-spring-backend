package six.team.backend.store;

/**
 * Created by remon on 14/09/2015.
 */
public class UserStore {
    private int id;
    private String username;

    public UserStore() {

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
