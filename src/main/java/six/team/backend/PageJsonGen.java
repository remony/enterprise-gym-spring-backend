package six.team.backend;

import six.team.backend.store.PageStore;

import java.util.Date;
import java.util.LinkedList;

/*
            PageJsonGen
            Author: Stuart Douglas
            Description: The purpose of this class is to generate the page specific json values
 */
public class PageJsonGen {
    public PageStore createPageJson(String title, String description, LinkedList content) {
        PageStore pageStore = new PageStore();
        pageStore.setTitle(title);


        return pageStore; //return the full page json
    }

}
