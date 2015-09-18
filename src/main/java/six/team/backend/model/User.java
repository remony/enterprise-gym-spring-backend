package six.team.backend.model;

import java.util.LinkedList;
import six.team.backend.dao.UserDAO;
import six.team.backend.store.UserLoginStore;
import six.team.backend.store.UserInfoStore;
import six.team.backend.store.UserStore;

public class User {

    public static LinkedList<UserInfoStore> getAll() {
        UserDAO userDAO = new UserDAO();
        return userDAO.list();
    }

    public static LinkedList<UserStore> getAllUnauthorised() {
        UserDAO userDAO = new UserDAO();
        return userDAO.unauthorisedList();
    }
    public static boolean approve(int userid, String usergroup) {
        UserDAO userDAO = new UserDAO();
        return userDAO.approveUser(userid, usergroup);
    }
    public static boolean delete(int userid) {
        UserDAO userDAO = new UserDAO();
        return userDAO.deleteUser(userid);
    }

    public static UserLoginStore verifyLogin(String username, String password){
        UserDAO userDAO = new UserDAO();
        return userDAO.verifyUser(username,password);

    }
}
