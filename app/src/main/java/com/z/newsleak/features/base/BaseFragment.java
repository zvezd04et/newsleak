package com.z.newsleak.features.base;

import android.content.Context;

import com.z.newsleak.moxy.MvpAppCompatFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseFragment extends MvpAppCompatFragment {

    @NonNull
    private String title;
    private boolean displayHome;
    @Nullable
    private BaseFragmentListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof BaseFragmentListener) {
            listener = (BaseFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    public void showTitle() {
        if (listener != null) {
            listener.setTitle(title, displayHome);
        }
    }

    protected void turnBack() {
        if (listener != null) {
            listener.turnBack();
        }
    }

    protected void setTitle(@NonNull String title, boolean displayHome) {
        this.title = title;
        this.displayHome = displayHome;
        showTitle();
    }
}