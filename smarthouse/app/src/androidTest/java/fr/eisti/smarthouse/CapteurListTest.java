package fr.eisti.smarthouse;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.eisti.smarthouse.view.activity.CapteursListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ErwanLBP on 05/02/18.
 */

@RunWith(AndroidJUnit4.class)
public class CapteurListTest {

    @Rule
    public ActivityTestRule<CapteursListActivity> capteursListActivity = new ActivityTestRule<CapteursListActivity>(CapteursListActivity.class);

    @Test
    public void cliqueItem() {
//        onView(withId(R.id.fcli_type_capteur)).perform(click());
    }
}
