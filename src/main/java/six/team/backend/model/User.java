package six.team.backend.model;

import six.team.backend.dao.UserDAO;
import six.team.backend.store.UserStore;

import java.util.LinkedList;

/**
 * Created by remon on 14/09/2015.
 */
public class User {

    public static LinkedList<UserStore> getAll() {
        UserDAO userDAO = new UserDAO();
        return userDAO.list();
    }

    public static LinkedList<UserStore> getAllUnauthorised() {
        UserDAO userDAO = new UserDAO();
        return userDAO.unauthorisedList();
    }
    public static void approve(int userid, String usergroup) {
        UserDAO userDAO = new UserDAO();
        userDAO.approveUser(userid,usergroup);
    }
    public static void delete(int userid) {
        UserDAO userDAO = new UserDAO();
        userDAO.deleteUser(userid);
    }

    public static String verifyLogin(String username, String password){
        UserDAO userDAO = new UserDAO();
        return userDAO.verifyUser(username,password);

    }
}
