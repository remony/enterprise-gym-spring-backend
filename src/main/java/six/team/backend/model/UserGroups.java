package six.team.backend.model;

import six.team.backend.dao.UserGroupDAO;
import six.team.backend.store.UserGroupsStore;

import java.util.LinkedList;

/**
 * Created by Anna on 22/09/2015.
 */
public class UserGroups {
    public static LinkedList<UserGroupsStore> getAll()
    {
        UserGroupDAO user = new UserGroupDAO();
        return user.getAll();
    }
    public static LinkedList<UserGroupsStore> get(String usergroup)
    {
        UserGroupDAO user = new UserGroupDAO();
        return user.get(usergroup);
    }
    public static boolean addUserGroup(String usergroup,int ea,int ee,int ed,int ev,int na,int nd,int ne,int nv,int pa,int pd,int pe,int pv,int ua,int ud,int ue,int uv)
    {
        UserGroupDAO user = new UserGroupDAO();
        UserGroupsStore userstore=new UserGroupsStore();
        userstore.setUsergroup(usergroup);
        userstore.setEventsadd(ea);
        userstore.setEventsdelete(ed);
        userstore.setEventsedit(ee);
        userstore.setEventsview(ev);
        userstore.setNewsadd(na);
        userstore.setNewsdelete(nd);
        userstore.setNewsedit(ne);
        userstore.setNewsview(nv);
        userstore.setPagesadd(pa);
        userstore.setPagesdelete(pd);
        userstore.setPagesedit(pe);
        userstore.setPagesview(pv);
        userstore.setUserssadd(ua);
        userstore.setUsersdelete(ud);
        userstore.setUsersedit(ue);
        userstore.setUsersview(uv);
        return user.add(userstore);
    }
    public static boolean deleteUserGroup(String usergroup)
    {
        UserGroupDAO user = new UserGroupDAO();
        return user.delete(usergroup);
    }
    public static boolean updateUserGroup(String usergroup,int ea,int ee,int ed,int ev,int na,int nd,int ne,int nv,int pa,int pd,int pe,int pv,int ua,int ud,int ue,int uv)
    {
        UserGroupDAO user = new UserGroupDAO();
        UserGroupsStore userstore=new UserGroupsStore();
        userstore.setUsergroup(usergroup);
        userstore.setEventsadd(ea);
        userstore.setEventsdelete(ed);
        userstore.setEventsedit(ee);
        userstore.setEventsview(ev);
        userstore.setNewsadd(na);
        userstore.setNewsdelete(nd);
        userstore.setNewsedit(ne);
        userstore.setNewsview(nv);
        userstore.setPagesadd(pa);
        userstore.setPagesdelete(pd);
        userstore.setPagesedit(pe);
        userstore.setPagesview(pv);
        userstore.setUserssadd(ua);
        userstore.setUsersdelete(ud);
        userstore.setUsersedit(ue);
        userstore.setUsersview(uv);
        return user.update(userstore);
    }

    public static boolean checkValidity(String usergroup) {
        UserGroupDAO usergroupdao = new UserGroupDAO();
        return usergroupdao.titleExists(usergroup);

    }
}
