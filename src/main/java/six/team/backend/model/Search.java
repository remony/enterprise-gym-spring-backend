package six.team.backend.model;

import six.team.backend.dao.EventDAO;
import six.team.backend.dao.NewsDAO;
import six.team.backend.dao.PagesDAO;
import six.team.backend.store.EventStore;
import six.team.backend.store.NewsStore;
import six.team.backend.store.PageStore;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Anna on 23/09/2015.
 */
public class Search {
    public static LinkedList<NewsStore> searchNews(String searchtext)
    {
        NewsDAO newsdao=new NewsDAO();
        LinkedList<NewsStore> searchresults=new LinkedList<NewsStore>();
        LinkedList<NewsStore> news=newsdao.getAll();
        ListIterator<NewsStore> listIterator = news.listIterator();
        while (listIterator.hasNext()) {
            NewsStore article=listIterator.next();
            String title=article.getTitle().toLowerCase();
           if(title.contains(searchtext.toLowerCase()))
           {
               searchresults.add(article);
           }
        }
        return searchresults;
    }

    public static LinkedList<EventStore> searchEvents(String searchtext)
    {
        EventDAO eventsdao=new EventDAO();
        LinkedList<EventStore> searchresults=new LinkedList<EventStore>();
        LinkedList<EventStore> events=eventsdao.listEvents();
        ListIterator<EventStore> listIterator = events.listIterator();
        while (listIterator.hasNext()) {
            EventStore event=listIterator.next();
            String title=event.getName().toLowerCase();
            if(title.contains(searchtext.toLowerCase()))
            {
                searchresults.add(event);
            }
        }
        return searchresults;
    }
    public static LinkedList<PageStore> searchPages(String searchtext)
    {
        PagesDAO pagesdao=new PagesDAO();
        LinkedList<PageStore> searchresults=new LinkedList<PageStore>();
        LinkedList<PageStore> pages=pagesdao.getAll();
        ListIterator<PageStore> listIterator = pages.listIterator();
        while (listIterator.hasNext()) {
            PageStore page=listIterator.next();
            String title=page.getTitle().toLowerCase();
            if(title.contains(searchtext.toLowerCase()))
            {
                searchresults.add(page);
            }
        }
        return searchresults;
    }
}
