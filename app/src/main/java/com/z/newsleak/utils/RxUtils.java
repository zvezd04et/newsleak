package com.z.newsleak.utils;

import androidx.annotation.Nullable;
import io.reactivex.disposables.Disposable;

public class RxUtils {
    public static void disposeSafely(@Nullable Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private RxUtils() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
    }
}
