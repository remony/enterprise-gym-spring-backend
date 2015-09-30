package six.team.backend;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import six.team.backend.dao.UserDAO;
import six.team.backend.store.UserStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class AppTests {
    private MockMvc mockMvc;
    private int eventid;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

     /*
     *********************** Index Controller *************************************************
    */
    @Test
    public void index() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void indexUpdate() throws Exception {
        mockMvc.perform(post("/admin/users").header("id", 1).header("title","test").header("description","test")).equals(status().isOk());
    }


    /*
     *********************** Admin Controller *************************************************
    */

    @Test
    public void unauthorisedUserList() throws Exception {
        mockMvc.perform(get("/admin/users").header("token", "dda9ee9e27c042fc9de9ede4b841216c")).andExpect(status().isOk());
    }

    @Test
    public void approveUnauthorisedUsers() throws Exception{
        mockMvc.perform(post("/admin/users").header("token", "dda9ee9e27c042fc9de9ede4b841216c").header("approvedGroup", "user").header("approvedStatus","approved").header("approvedId", 2)).andExpect(status().isOk());
    }

    /*
    ****************************AuthenticationController *****************************************
     */

    @Test
    public void authentication() throws Exception{
        mockMvc.perform(post("/auth").header("token", "dda9ee9e27c042fc9de9ede4b841216c").header("username", "testAdmin")).andExpect(status().isOk());
    }

    /*
    ****************************CalendarController *****************************************
     */

    @Test
    public void calendar() throws Exception{
        mockMvc.perform(get("/calendar")).andExpect(status().isOk());
    }

    /*
    *******************************EventController*****************************************************
     */

    @Test
    public void allEvents() throws Exception{
        mockMvc.perform(get("/events")).andExpect(status().isOk());
    }

    @Test
    public void createEvent() throws Exception{
        //mockMvc.perform(post("/events/insert").header("name","test").header("location","test").header("venue","test").header("description","test").header("points",10).header("points_category","test").header("startdate", "Wed Sep 30 2015 16:22:03 GMT +0100").header("enddate","Wed Sep 30 2015 16:22:03 GMT +0100").header("token", "dda9ee9e27c042fc9de9ede4b841216c")).andExpect(status().isOk());
    }

    @Test
    public void singleEvents() throws Exception{
        mockMvc.perform(get("/events/1")).andExpect(status().isOk());
    }

    @Test
    public void updateEvent() throws Exception{
        mockMvc.perform(post("/events/update/1").header("name","test").header("location","test").header("venue","test").header("description","test").header("points",10).header("points_category","test").header("startdate", "Wed Sep 30 2015 16:22:03 GMT +0100").header("enddate","Wed Sep 30 2015 16:22:03 GMT +0100").header("token", "dda9ee9e27c042fc9de9ede4b841216c")).andExpect(status().isOk());
    }

    @Test
    public void signUp() throws Exception{
        //mockMvc.perform(post("/events/44/signup").header("token", "dda9ee9e27c042fc9de9ede4b841216c")).andExpect(status().isOk());
    }

    @Test
    public void participants() throws Exception{
        mockMvc.perform(get("/events/44/participants").header("token", "dda9ee9e27c042fc9de9ede4b841216c")).andExpect(status().isOk());
    }

    @Test
    public void updateAttendance() throws Exception{
        mockMvc.perform(post("/events/44/participants").header("token", "dda9ee9e27c042fc9de9ede4b841216c").header("attendeeid", 15).header("attendance", 1)).andExpect(status().isOk());
    }

    @Test
    public void userEvents() throws Exception{
        mockMvc.perform(get("/events/user/testAdmin").header("token", "dda9ee9e27c042fc9de9ede4b841216c")).andExpect(status().isOk());
    }

    @Test
    public void upcomingEvents() throws Exception{
        mockMvc.perform(get("/events/upcoming"));
    }

    /*
    ********************************News Controller ************************************
    */

    @Test
    public void allNews() throws Exception{
        mockMvc.perform(get("/news").header("pagesize",5).header("page",1)).andExpect(status().isOk());
    }

    @Test //couldn't figure how to use the request body in mock mvc
    public void createNews() throws Exception{
        //mockMvc.perform(post("/news").header("token", "dda9ee9e27c042fc9de9ede4b841216c").header("title","test").content("test").header("permission").contentType(MediaType.TEXT_PLAIN)).andExpect(status().isOk());
    }

    @Test
    public void updateNews() throws Exception{
        mockMvc.perform(post("/news/test").header("token", "dda9ee9e27c042fc9de9ede4b841216c").header("title", "test").header("permission", "admin").content("test")).andExpect(status().isOk());
    }

    @Test
    public void getPages() throws Exception{
        mockMvc.perform(get("/pages")).andExpect(status().isOk());
    }

    @Test
    public void getAllPoints() throws Exception{
        mockMvc.perform(get("/points").header("token", "dda9ee9e27c042fc9de9ede4b841216c")).andExpect(status().isOk());
    }

    @Test
    public void getUserPoints() throws Exception{
        mockMvc.perform(get("/points/testAccount").header("token", "dda9ee9e27c042fc9de9ede4b841216c")).andExpect(status().isOk());
    }

    @Test
    public void getQuizzes() throws Exception{
        mockMvc.perform(get("/quiz")).andExpect(status().isOk());
    }

    @Test
    public void getSearch() throws Exception{
        mockMvc.perform(get("/search/news").header("searchtext", "generating")).andExpect(status().isOk());
    }

    public void getAllUsers() throws Exception{
        mockMvc.perform(get("/users").header("token", "dda9ee9e27c042fc9de9ede4b841216c")).andExpect(status().isOk());
    }

    public void getSingleUsers() throws Exception{
        mockMvc.perform(get("/users/testAccount").header("token", "dda9ee9e27c042fc9de9ede4b841216c")).andExpect(status().isOk());
    }

    public void getUserGroups() throws Exception{
        mockMvc.perform(get("/usergroup").header("token", "dda9ee9e27c042fc9de9ede4b841216c")).andExpect(status().isOk());
    }




    /*
    @Test
    public void getAllFiles() throws Exception {
        mockMvc.perform(get("/files"))
                .andExpect(status().isOk());
    }

    @Test
    // This should pass since we want to pass an empty json: 200 is valid http status code for this behaviour
    public void getNonExistentFile() throws Exception {
        mockMvc.perform(get("/file/afile"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllFilesForEventID() throws Exception {
        mockMvc.perform(get("/files/event/123"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllFilesForUnNamedType() throws Exception {
        mockMvc.perform(get("/files/NEWSASJNDASD/123"))
                .andExpect(status().isOk());
    }


    private static Connection getDBConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String db = "jdbc:mysql://46.101.32.73:3306/enterprisegym";
            connection = DriverManager.getConnection(db, "admin", "admin");


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }


    // requires a SPIKE - does not work ("Caused by: java.lang.ClassNotFoundException: javax.servlet.SessionCookieConfig")

    @Test
    public void insertNewUser() {

        //Connection connection = null;
        //connection = getDBConnection();

        try {
            Calendar cal = Calendar.getInstance();

            UserStore mockup = new UserStore();
            mockup.setUsername("billgates1");
            mockup.setPassword("baguettes");
            mockup.setFirstname("Bill");
            mockup.setLastname("Gates");
            mockup.setGender("m");
            mockup.setEmail("bgate1s@ms.com");
            mockup.setContactnumber("123456");
            mockup.setCountry("UK");
            mockup.setUniversity("UoD");
            mockup.setStatus("Undergraduate");
            mockup.setSubject("CompSci");
            mockup.setYearofstudy(2015);
            mockup.setMatricnumber("123123");
            mockup.setYoung_es(1);
            mockup.setUsergroup("user");
            mockup.setToken("someToken");
            mockup.setRegistration_date(new java.sql.Timestamp(cal.getTimeInMillis()));
            UserDAO userDAO = new UserDAO();
            assertEquals(0, userDAO.Save(mockup));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
*/
}
