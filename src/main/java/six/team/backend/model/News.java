package six.team.backend.model;

import six.team.backend.dao.NewsDAO;
import six.team.backend.store.NewsStore;

import java.util.LinkedList;

/**
 * Created by remon on 14/09/2015.
 */
public class News {

    public static LinkedList<NewsStore> getAll() {
        NewsDAO newsDAO = new NewsDAO();
        return newsDAO.list();
    }

    public static boolean delete(int userId){
        NewsDAO newsDAO = new NewsDAO();
        return newsDAO.delete(userId);
    }

    public static void update(int userId){
        NewsDAO newsDAO = new NewsDAO();
        newsDAO.update(userId);
    }
    public static void save(NewsStore newsStore){
        NewsDAO newsDAO = new NewsDAO();
        newsDAO.save(newsStore);
    }
}
