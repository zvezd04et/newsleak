package com.z.newsleak.features.news_edit;

import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

import java.util.Calendar;
import androidx.annotation.Nullable;

public class NewsEditViewState implements ViewState<NewsEditContract.View> {

    @Nullable
    private Calendar calendar;

    @Override
    public void apply(NewsEditContract.View view, boolean retained) {
        if (calendar != null) {
            view.setCalendar(calendar);
        }
    }

    public void setCalendar(@Nullable Calendar calendar) {
        this.calendar = calendar;
    }
}
