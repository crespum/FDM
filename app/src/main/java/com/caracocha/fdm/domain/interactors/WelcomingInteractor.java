package com.caracocha.fdm.domain.interactors;


import com.caracocha.fdm.domain.interactors.base.Interactor;


public interface WelcomingInteractor extends Interactor {

    interface Callback {
        void onMessageRetrieved(String message);

        void onRetrievalFailed(String error);
    }
}
