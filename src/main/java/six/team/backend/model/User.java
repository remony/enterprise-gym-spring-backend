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

    public static UserInfoStore getUser(String userName) {
        UserDAO userDAO = new UserDAO();
        return userDAO.getUserInfo(userName);
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
    public static boolean update(int userid, String username,  String firstname,
                                 String secondname, String gender, String email,String contactnumber, String country,
                                 String university, String status, String subject,
                                 String matricnumber, int young_es, String usergroup,
                                 int yearofstudy, String bio) {
        UserDAO userDAO = new UserDAO();
        UserStore user=new UserStore();
        user.setUsergroup(usergroup);
        user.setBio(bio);
        user.setContactnumber(contactnumber);
        user.setCountry(country);
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setLastname(secondname);
        user.setId(userid);
        user.setMatricnumber(matricnumber);
        user.setGender(gender);
        user.setStatus(status);
        user.setSubject(subject);
        user.setUniversity(university);
        user.setUsername(username);
        user.setYearofstudy(yearofstudy);
        user.setYoung_es(young_es);
        return userDAO.updateUser(user);
    }

    public static UserLoginStore verifyLogin(String username, String password){
        UserDAO userDAO = new UserDAO();
        return userDAO.verifyUser(username,password);

    }

    public static boolean resetPassword(String username, String newpassword){
        UserDAO userDAO = new UserDAO();
        return userDAO.resetPassword(username,newpassword);

    }
}
