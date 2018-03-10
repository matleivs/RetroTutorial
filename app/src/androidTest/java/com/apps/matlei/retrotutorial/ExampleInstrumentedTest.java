package com.apps.matlei.retrotutorial;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


//    @Rule
//    public ActivityTestRule<GamePanel> <-- use this when testing an Activity or Fragment --> so i will test MainActivity here
    // see https://android.jlelse.eu/the-basics-of-android-espresso-testing-activities-fragments-7a8bfbc16dc5

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.apps.matlei.retrotutorial", appContext.getPackageName());
    }
}