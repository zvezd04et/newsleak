package com.z.newsleak.features.intro;

import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

public class IntroViewState implements ViewState<IntroContract.View> {

    private boolean isIntroVisible;

    @Override
    public void apply(IntroContract.View view, boolean retained) {
        if (isIntroVisible) {
            view.setIntroLayout();
        }
    }

    public void setIntroVisible(boolean introVisible) {
        isIntroVisible = introVisible;
    }
}
