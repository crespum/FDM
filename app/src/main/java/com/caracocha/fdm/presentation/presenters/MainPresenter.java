package com.caracocha.fdm.presentation.presenters;

import com.caracocha.fdm.presentation.presenters.base.BasePresenter;
import com.caracocha.fdm.presentation.ui.BaseView;


public interface MainPresenter extends BasePresenter {

    interface View extends BaseView {
        void displayWelcomeMessage(String msg);
    }
}
