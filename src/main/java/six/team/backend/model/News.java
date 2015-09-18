package six.team.backend.model;

import six.team.backend.dao.NewsDAO;
import six.team.backend.store.NewsStore;

import java.util.Date;
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
        newsDAO.update(userId, title, text);
    }
    public static void save(String title, String text, String permission, String category){
        NewsDAO newsDAO = new NewsDAO();
        NewsStore news = new NewsStore();
        Date date=new Date();
        news.setDateCreated(date);
        news.setLastedited(date);
        news.setTitle(title);
        news.setText(text);
        news.setCategory(category);
        news.setPermission(permission);
        news.setSlug(generateSlug(title));
        newsDAO.save(news);
    }

    public static String generateSlug(String title)
    {
        String slug="";
        for(String token : title.split("\\s+"))
            slug+=token+"-";
        slug = slug.substring(0, slug.length()-1);
        
        return slug;
    }
}
