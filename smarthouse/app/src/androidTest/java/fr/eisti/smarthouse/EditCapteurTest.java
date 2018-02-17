package fr.eisti.smarthouse;


import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
//import org.powermock.core.classloader.annotations.PrepareForTest;

import fr.eisti.smarthouse.model.Capteur;
import fr.eisti.smarthouse.presenter.EditCapteurPresenter;
import fr.eisti.smarthouse.view.activity.EditCapteurActivity;
import fr.eisti.smarthouse.view.fragment.EditCapteurFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
//import static org.powermock.api.mockito.PowerMockito.mockStatic;
//import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by ErwanLBP on 05/02/18.
 */

@RunWith(MockitoJUnitRunner.class)
//@PrepareForTest({FirebaseAuth.class})
public class EditCapteurTest {

    @Rule
    public ActivityTestRule<EditCapteurActivity> activityTestRule = new ActivityTestRule<EditCapteurActivity>(EditCapteurActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent i = super.getActivityIntent();
            i.putExtra(Capteur.NAME, "Cave 1");
            return i;
        }
    };

    @BeforeClass
    public static void init() {
//        mockStatic(FirebaseAuth.class);
//        when(FirebaseAuth.getInstance().getCurrentUser()).thenReturn(mock(FirebaseUser.class));
    }

    @Test
    public void fillFrom() {
        EditCapteurFragment fragment = ((EditCapteurFragment) activityTestRule.getActivity().getFragmentManager().findFragmentById(R.id.aec_fragment_edit));

        EditCapteurPresenter presenter = mock(EditCapteurPresenter.class); // public EditCapteurPresenter(EditCapteurFragment)

        // Seul facon d'accéder au Presenter du fragment
        fragment.setPresenter(presenter);

        // Mock presenter.findInfos() -> fragment.fillFrom()
        doAnswer(invocation -> {
            fragment.fillFrom(new Capteur("Cave 1", "TEMPERATURE", true));
            return null;
        }).when(presenter).findInfos("Cave 1");

        // Check que tout est rempli
        onView(withId(R.id.fec_name_capteur)).check(matches(withText("Cave 1")));
        onView(withId(R.id.fec_type_capteur)).check(matches(withText("TEMPERATURE")));
        onView(withId(R.id.fec_activ_capteur)).check(matches(isChecked()));
    }

    @Test
    public void switchActiv() {
//        EditCapteurPresenter presenter = mock(EditCapteurPresenter.class);

//        when(presenter.findInfos("foo")).then()
//        onView(withId(R.id.fec_activ_capteur)).perform(click()).check()
//
//        verify(presenter).activateCapteur();
    }
}
