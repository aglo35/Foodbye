import com.hill.variety.LoginActivity;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Erko on 14.10.2015.
 */
public class LoginActivityTest extends LoginActivity {

    @org.junit.Test
    public void testPreconditions() {
        assertNotNull("LoginActivity is null", LoginActivity.class);
    }
}