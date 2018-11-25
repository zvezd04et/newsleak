package com.z.newsleak.features.intro;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.utils.SupportUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class IntroPresenter extends MvpBasePresenter<IntroContract.View> implements IntroContract.Presenter{

    private static final long TIME_DELAY = 2;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PreferencesManager preferencesManager;

    public IntroPresenter(PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
    }

    @Override
    public void destroy() {
        super.destroy();
        SupportUtils.disposeSafely(compositeDisposable);
    }

    @Override
    public void makeDelay() {
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
