package six.team.backend.store;
import java.sql.Date;

/**
 * Created by Christopher on 17/09/2015.
 */
public class UserInfoStore {
    private int id;
    private int yearOfStudy;
    private int mobile;
    private String username;
    private String gender;
    private String contactNo;
    private String matricNo;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String university;
    private String status;
    private String degreeSubject;
    private String userGroup;
    private Date regDate;
    private int young_e_s;

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int isYoung_e_s() {
        return young_e_s;
    }

    public void setYoung_e_s(int young_e_s) {
        this.young_e_s = young_e_s;
    }

    public UserInfoStore() {

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactNo() {
        return this.contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public int getYearOfStudy() {
        return this.yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getMatricNo() {
        return this.matricNo;
    }

    public void setMmtricNo(String matricNo) {
        this.matricNo = matricNo;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUniversity() {
        return this.university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDegreeSubject() {
        return this.degreeSubject;
    }

    public void setDegreeSubject(String degreeSubject) {
        this.degreeSubject = degreeSubject;
    }

    public String getUserGroup() {
        return this.userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

}
