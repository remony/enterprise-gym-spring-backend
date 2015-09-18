package six.team.backend;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class AppTests {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void simple() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("hello"));
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

    @Test
    public void insertNewUser() {

        Connection connection = null;
        connection = getDBConnection();

        try {
            PreparedStatement psUsernameChecker = connection.prepareStatement("INSERT INTO Users (username, password, firstname, lastname, gender, email, contactnumber, country, university, status, subject, year, matricnumber, young_es, usergroup, mobile, activated, token, registration_date) VALUES ('billgates', 'baguettes', 'Bill', 'Gates', 'm', 'bgates@ms.com', '123456', 'UK', 'UoD', 'Undergraduate', 'CompSci', 2015, '123123', 'ES1', 'Students', '077123456', '0', 'token1', '01/01/2015 00:01')");

            // fetch in a result set and count if the results match
        }
        catch (SQLException e)
        {
            // do something appropriate with the exception, *at least*:
            e.printStackTrace();
        }

    }

}
