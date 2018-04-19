package studio.brunocasamassa.myapplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import studio.brunocasamassa.myapplication.activities.MainActivity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;


@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Test
    public void httpRequestIsValid() throws Exception {
        assertNotNull(shadowOf(RuntimeEnvironment.application));

        int httpCode = Robolectric.buildActivity(MainActivity.class).create().get().verifyTestRequestHttp();

        //range valido
        assertTrue(httpCode >= 200 && httpCode <= 300);
    }
}

