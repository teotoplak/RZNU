import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;


import controllers.Users;
import org.junit.Test;

import play.mvc.Result;
import play.twirl.api.Content;

public class UsersTest {

    @Test
    public void testIndex() {
        Result result = new Users().readUsers();
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType().get());
    }

}