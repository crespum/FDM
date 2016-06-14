package com.caracocha.fdm.domain.repository;

import com.caracocha.fdm.domain.model.Event;
import com.caracocha.fdm.domain.model.ItemsList;

import java.util.ArrayList;

/**
 * Created by xabi@crespum.eu on 6/13/16.
 */
public interface CeltiaRepository {
    ItemsList retrieveEventsList();
    Event retrieveEvent(int id);
}
