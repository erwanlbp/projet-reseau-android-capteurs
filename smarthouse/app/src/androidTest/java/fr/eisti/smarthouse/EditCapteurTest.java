package fr.eisti.smarthouse;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import fr.eisti.smarthouse.presenter.EditCapteurPresenter;
import fr.eisti.smarthouse.view.activity.EditCapteurActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ErwanLBP on 05/02/18.
 */

@RunWith(AndroidJUnit4.class)
public class EditCapteurTest {

    @Rule
    public ActivityTestRule<EditCapteurActivity> editCapteurActivity = new ActivityTestRule<EditCapteurActivity>(EditCapteurActivity.class);

    @BeforeClass
    public void init() {
        EditCapteurPresenter presenter = Mockito.mock(EditCapteurPresenter.class);
    }

    @Test
    public void scrollList() {
        onView(withId(R.id.aec_fragment_data)).perform(scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void detailCapteur() {
        onView(withId(R.id.fec_name_capteur)).check(matches(isDisplayed()));
        onView(withId(R.id.fec_type_capteur)).check(matches(isDisplayed()));
    }

//    @Test
//    public void switchActiv() {
//
//    onView(withId(R.id.fec_activ_capteur)).perform(click()).check()
//    }
}
