package com.z.newsleak.features.base;

import androidx.annotation.NonNull;

public interface BaseFragmentListener {

    void setTitle(@NonNull String title, boolean displayHome);

    void turnBack();
}