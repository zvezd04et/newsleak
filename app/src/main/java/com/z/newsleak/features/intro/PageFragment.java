package com.z.newsleak.features.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.z.newsleak.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PageFragment extends Fragment {

    private static final String BUNDLE_IMAGE_KEY = "BUNDLE_IMAGE_KEY";

    public static PageFragment newInstance(@DrawableRes int drawableRes) {
        final Bundle args = new Bundle();
        args.putInt(BUNDLE_IMAGE_KEY, drawableRes);

        final PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_intro_page, container, false);

        final Bundle args = getArguments();
        if (args == null) {
            return view;
        }

        final int drawableRes = args.getInt(BUNDLE_IMAGE_KEY);
        final ImageView imageView = view.findViewById(R.id.intro_page_iv_screen);
        imageView.setImageResource(drawableRes);

        return view;
    }
}