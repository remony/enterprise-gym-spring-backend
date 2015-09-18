package six.team.backend.model;

import six.team.backend.dao.AuthDAO;
import six.team.backend.dao.UserDAO;
import six.team.backend.store.UserStore;

import java.util.LinkedList;

/**
 * Created by Gareth on 18/09/2015.
 */
public class Auth {

    public static boolean verify(String token, String username){
        AuthDAO authDAO = new AuthDAO();
        return authDAO.verifyUser(token,username);

    }
}
