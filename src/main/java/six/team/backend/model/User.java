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
    public static boolean approve(int userid, String usergroup) {
        UserDAO userDAO = new UserDAO();
        return userDAO.approveUser(userid,usergroup);
    }
    public static boolean delete(int userid) {
        UserDAO userDAO = new UserDAO();
        return userDAO.deleteUser(userid);
    }

    public static String verifyLogin(String username, String password){
        UserDAO userDAO = new UserDAO();
        return userDAO.verifyUser(username,password);

    }
}
