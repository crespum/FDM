package com.caracocha.fdm.domain.interactors;

import com.caracocha.fdm.domain.executor.Executor;
import com.caracocha.fdm.domain.executor.MainThread;
import com.caracocha.fdm.domain.interactors.base.AbstractInteractor;
import com.caracocha.fdm.domain.model.ItemsList;
import com.caracocha.fdm.domain.repository.CeltiaRepository;

/**
 * Created by xabi@crespum.eu on 6/13/16.
 */
public class GetEventListImpl extends AbstractInteractor implements GetEventList {

    private GetEventList.Callback mCallback;
    private CeltiaRepository mCeltiaRepository;

    public GetEventListImpl(Executor threadExecutor,
                            MainThread mainThread,
                            GetEventList.Callback callback,
                            CeltiaRepository celtiaRepository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mCeltiaRepository = celtiaRepository;
    }

    @Override
    public void run() {
        try {
            ItemsList list = mCeltiaRepository.retrieveEventsList();
            postMessage(list);
        } catch (Exception e) {
            notifyError();
        }
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrievalError();
            }
        });
    }

    private void postMessage(final ItemsList list) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onEventsListRetrieved(list);
            }
        });
    }
}
