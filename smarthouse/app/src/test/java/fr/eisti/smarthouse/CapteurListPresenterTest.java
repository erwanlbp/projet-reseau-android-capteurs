package fr.eisti.smarthouse;

import android.view.View;
import android.widget.TextView;

import org.junit.Test;

import fr.eisti.smarthouse.presenter.CapteursListPresenter;
import fr.eisti.smarthouse.view.fragment.CapteursListFragment;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ErwanLBP on 10/02/18.
 */

public class CapteurListPresenterTest {

    @Test
    public void itemClicked() {
        // Mocks
        CapteursListFragment fragment = mock(CapteursListFragment.class);
        View view = mock(View.class);
        TextView textView = mock(TextView.class);
        when(textView.toString()).thenReturn("Cave 1");
        when(view.findViewById(R.id.fcli_name_capteur)).thenReturn(textView);

        // Test data
        CapteursListPresenter presenter = new CapteursListPresenter(fragment);

        // Actual test
        presenter.itemClicked(view);

        // Verification
        verify(fragment).startEditActivity("Cave 1");
    }

    @Test
    public void addNew() {
        CapteursListFragment fragment = mock(CapteursListFragment.class);

        CapteursListPresenter presenter = new CapteursListPresenter(fragment);

        presenter.addNew();

        verify(fragment).startEditActivity(null);
    }
}
