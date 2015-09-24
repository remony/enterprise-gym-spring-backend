package six.team.backend.store;

/**
 * Created by Gareth on 23/09/2015.
 */
public class PointStore {
    private int userid;
    private int enterprise_challenge;
    private int theory;
    private int project;
    private int action;
    private int virtual;
    private int total;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getEnterprise_challenge() {
        return enterprise_challenge;
    }

    public void setEnterprise_challenge(int enterprise_challenge) {
        this.enterprise_challenge = enterprise_challenge;
    }

    public int getTheory() {
        return theory;
    }

    public void setTheory(int theory) {
        this.theory = theory;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getVirtual() {
        return virtual;
    }

    public void setVirtual(int virtual) {
        this.virtual = virtual;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
