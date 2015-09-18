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

    public static NewsStore get(int newsId) {
        NewsDAO newsDAO = new NewsDAO();
        return newsDAO.get(newsId);
    }

    public static void delete(int newsId){
        NewsDAO newsDAO = new NewsDAO();
        newsDAO.delete(newsId);
    }

    public static void update(int userId,String title, String text){
        NewsDAO newsDAO = new NewsDAO();
        newsDAO.update(userId,title,text);
    }
    public static void save(NewsStore newsStore){
        NewsDAO newsDAO = new NewsDAO();
        newsDAO.save(newsStore);
    }
}
