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

    public static NewsStore get(String slug) {
        NewsDAO newsDAO = new NewsDAO();
        return newsDAO.get(slug);
    }

    public static boolean delete(String slug){
        NewsDAO newsDAO = new NewsDAO();
        return newsDAO.delete(slug);

    }

    public static boolean update(String slug,String title, String text, String permission){
        NewsDAO newsDAO = new NewsDAO();
        NewsStore news = new NewsStore();
        Date date=new Date();
        news.setLastedited(date);
        news.setTitle(title);
        news.setText(text);
        news.setPermission(permission);
        news.setSlug(generateSlug(title));
        return newsDAO.update(news,slug);
    }
    public static boolean save(String title, String text, String permission){
        NewsDAO newsDAO = new NewsDAO();
        NewsStore news = new NewsStore();
        Date date=new Date();
        news.setDateCreated(date);
        news.setLastedited(date);
        news.setTitle(title);
        news.setText(text);
        news.setPermission(permission);
        news.setSlug(generateSlug(title));
        return newsDAO.save(news);
    }

    public static String generateSlug(String title)
    {
        String slug="";
        for(String token : title.split("\\s+"))
            slug+=token+"-";
        slug = slug.substring(0, slug.length()-1);
        return slug.toLowerCase();
    }
    public static boolean checkValitity(String title)
    {
        NewsDAO newsDAO = new NewsDAO();
        return newsDAO.titleExists(title);

    }

}
