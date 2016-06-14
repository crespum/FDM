package com.caracocha.fdm.domain.interactors;

import com.caracocha.fdm.domain.executor.Executor;
import com.caracocha.fdm.domain.executor.MainThread;
import com.caracocha.fdm.domain.repository.CeltiaRepository;
import com.caracocha.fdm.threading.TestMainThread;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;


/**
 * Tests our welcoming interactor.
 */
public class GetWelcomeMessageTest {

    private MainThread mMainThread;
    @Mock
    private Executor mExecutor;
    @Mock
    private CeltiaRepository mMessageRepository;
    @Mock
    private GetEventList.Callback mMockedCallback;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMainThread = new TestMainThread();
    }

    @Test
    public void testWelcomeMessageFound() throws Exception {

        when(mMessageRepository.retrieveEventsList())
                .thenReturn(null);

        GetEventListImpl interactor = new GetEventListImpl(mExecutor, mMainThread, mMockedCallback, mMessageRepository);
        interactor.run();

        Mockito.verify(mMessageRepository).retrieveEventsList();
        Mockito.verifyNoMoreInteractions(mMessageRepository);
        Mockito.verify(mMockedCallback).onEventsListRetrieved(null);
    }
}
