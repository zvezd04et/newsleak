package com.z.newsleak.features.intro;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.z.newsleak.data.PreferencesManager;

import javax.inject.Inject;

import androidx.annotation.NonNull;

@InjectViewState
public class IntroPresenter extends MvpPresenter<IntroView> {

    @NonNull
    private PreferencesManager preferencesManager;

    @Inject
    public IntroPresenter(@NonNull PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        if (preferencesManager.isFirstTimeLaunch()) {
            preferencesManager.setFirstTimeLaunch(false);
            getViewState().setIntroLayout();
        } else {
            getViewState().startNextActivity();
        }
    }

}
