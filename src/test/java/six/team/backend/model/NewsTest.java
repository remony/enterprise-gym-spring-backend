package six.team.backend.model;

import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
/**
 * Created by Anna on 20/09/2015.
 */
public class NewsTest extends TestCase {

    public void testGenerateSlug() throws Exception {
        News news = new News();
        String test_title="This iS tEst tiTle";
        String test_slug="this-is-test-title";
        assertEquals(news.generateSlug(test_title), test_slug);
    }
}