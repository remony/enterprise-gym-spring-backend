package six.team.backend.model;

import six.team.backend.dao.IndexDAO;
import six.team.backend.store.IndexStore;

/**
 * Created by Gareth on 18/09/2015.
 */
public class Index {
    public static IndexStore getIndex(){
        IndexDAO indexDAO = new IndexDAO();
        return indexDAO.getIndex();

    }
}
