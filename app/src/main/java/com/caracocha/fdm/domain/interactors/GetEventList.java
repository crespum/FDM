package com.caracocha.fdm.domain.interactors;

import com.caracocha.fdm.domain.interactors.base.Interactor;
import com.caracocha.fdm.domain.model.Event;
import com.caracocha.fdm.domain.model.ItemsList;

import java.util.ArrayList;

/**
 * Created by xabi@crespum.eu on 6/13/16.
 */
public interface GetEventList extends Interactor {

    interface Callback {
        void onEventsListRetrieved(ItemsList events);

        void onRetrievalError();
    }
}