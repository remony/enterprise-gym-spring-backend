package six.team.backend.store;

/**
 * Created by Gareth on 18/09/2015.
 */
public class UserLoginStore {

        private String username;
        private String message;
        private String usergroup;
        private String token;
        private boolean loggedIn;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUsergroup() {
            return usergroup;
        }

        public void setUsergroup(String usergroup) {
            this.usergroup = usergroup;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
}
