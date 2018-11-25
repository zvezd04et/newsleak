package com.z.newsleak.features.intro;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface IntroContract {

    interface View extends MvpView {
        void setIntroLayout();

        void startNextActivity();
    }

    interface Presenter extends MvpPresenter<View> {

    }
}
