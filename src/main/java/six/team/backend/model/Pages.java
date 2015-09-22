package six.team.backend.model;

import org.json.JSONObject;
import six.team.backend.dao.NewsDAO;
import six.team.backend.dao.PagesDAO;
import six.team.backend.store.CommentStore;
import six.team.backend.store.PageStore;

import java.util.LinkedList;


public class Pages {

    public static JSONObject getHierarchy(){
        PagesDAO pages= new PagesDAO();
        return pages.getHierarchy();
    }
    public static boolean addPage(String parentSlug,String title,String description, String text,String permission,int order) {
        PagesDAO pagesDAO = new PagesDAO();
        PageStore pages=new PageStore();
        pages.setText(text);
        pages.setSlug(generateSlug(title));
        pages.setPermission(permission);
        pages.setDescription(description);
        pages.setTitle(title);
        pages.setParentSlug(parentSlug);
        pages.setOrder(order);
        return pagesDAO.addPage(pages);
    }

    public static boolean deletePage(String slug) {
        PagesDAO pagesDAO = new PagesDAO();
        return pagesDAO.deletePage(slug);
    }



    public static boolean editPage(String slug,String parentSlug,String title,String description, String text,String permission,int order) {
        PagesDAO pagesDAO=new PagesDAO();
        PageStore pages=new PageStore();
        pages.setText(text);
        pages.setSlug(generateSlug(title));
        pages.setPermission(permission);
        pages.setDescription(description);
        pages.setTitle(title);
        pages.setParentSlug(parentSlug);
        pages.setOrder(order);
        return pagesDAO.editPage(pages, slug);
    }

    public static LinkedList<PageStore> getAll() {
        PagesDAO page = new PagesDAO();
        return page.getAll();
    }
    public static LinkedList<PageStore> get(String slug) {
        PagesDAO page = new PagesDAO();
        return page.get(slug);
    }


    public static String generateSlug(String title) {
        String slug = "";
        for (String token : title.split("\\s+"))
            slug += token + "-";
        slug = slug.substring(0, slug.length() - 1);
        return slug.toLowerCase();
    }


    public static boolean checkValidity(String title) {
        PagesDAO pagesDAO = new PagesDAO();
        return pagesDAO.titleExists(title);

    }
}
