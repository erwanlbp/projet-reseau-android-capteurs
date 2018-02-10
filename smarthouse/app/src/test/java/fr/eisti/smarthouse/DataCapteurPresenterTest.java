package fr.eisti.smarthouse;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import fr.eisti.smarthouse.presenter.DataCapteurPresenter;
import fr.eisti.smarthouse.provider.FirebaseCapteurProvider;
import fr.eisti.smarthouse.view.fragment.DataCapteurFragment;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by ErwanLBP on 08/02/18.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(FirebaseCapteurProvider.class)
public class DataCapteurPresenterTest {

    @Test
    public void fillDataList() {
        // Mocks
        mockStatic(FirebaseCapteurProvider.class);
        DataCapteurFragment fragment = mock(DataCapteurFragment.class);

        // Test data
        DataCapteurPresenter presenter = new DataCapteurPresenter(fragment);

        // Actual test
        presenter.fillDataList("Cave 1");

        // Verification
        verify(FirebaseCapteurProvider.class);
    }
}
