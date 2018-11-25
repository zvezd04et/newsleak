package com.z.newsleak.features.intro;

import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.features.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

public class IntroPresenter extends BasePresenter<IntroContract.View> implements IntroContract.Presenter {

    private static final long TIME_DELAY = 2;
    private PreferencesManager preferencesManager;

    public IntroPresenter(PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        boolean showIntro = preferencesManager.isIntroVisible();

        if (!showIntro) {
            ifViewAttached(IntroContract.View::startNextActivity);
            return;
        }

        ifViewAttached(IntroContract.View::setIntroLayout);
        Disposable disposable = Completable.complete()
                .delay(TIME_DELAY, TimeUnit.SECONDS)
                .subscribe(() -> ifViewAttached(IntroContract.View::startNextActivity));
        compositeDisposable.add(disposable);
    }
}
