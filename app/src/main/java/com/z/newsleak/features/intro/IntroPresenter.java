package com.z.newsleak.features.intro;

import com.arellomobile.mvp.InjectViewState;
import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.features.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class IntroPresenter extends BasePresenter<IntroView> {

    private static final long TIME_DELAY = 2;
    @NonNull
    private PreferencesManager preferencesManager;

    public IntroPresenter(@NonNull PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        boolean showIntro = preferencesManager.isIntroVisible();

        if (!showIntro) {
            getViewState().startNextActivity();
            return;
        }

        getViewState().setIntroLayout();
        Disposable disposable = Completable.complete()
                .delay(TIME_DELAY, TimeUnit.SECONDS)
                .subscribe(() -> getViewState().startNextActivity());
        compositeDisposable.add(disposable);
    }

}
