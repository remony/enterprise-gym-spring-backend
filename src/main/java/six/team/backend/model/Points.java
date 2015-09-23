package six.team.backend.model;

import six.team.backend.dao.NewsDAO;
import six.team.backend.dao.PointDAO;
import six.team.backend.store.NewsStore;
import six.team.backend.store.PointStore;

import java.util.LinkedList;

/**
 * Created by Gareth on 23/09/2015.
 */
public class Points {
    public static boolean updatePoints(int userid, int points, String category) {
        PointDAO pointDAO = new PointDAO();
        return pointDAO.updatePoints(userid,points,category);
    }
}
