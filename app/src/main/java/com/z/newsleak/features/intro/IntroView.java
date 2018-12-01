package com.z.newsleak.features.intro;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

interface IntroView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void setIntroLayout();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startNextActivity();

}
