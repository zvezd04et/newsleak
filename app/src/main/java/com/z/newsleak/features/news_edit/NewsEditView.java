package com.z.newsleak.features.news_edit;

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.z.newsleak.features.base.BaseNewsItemView;

import java.util.Date;

import androidx.annotation.Nullable;

public interface NewsEditView extends BaseNewsItemView {

    @StateStrategyType(SingleStateStrategy.class)
    void showDatePickerDialog(int publishedYear, int publishedMonth, int publishedDay);

    @StateStrategyType(SingleStateStrategy.class)
    void showTimePickerDialog(int publishedHour, int publishedMinute);

    @StateStrategyType(SingleStateStrategy .class)
    void setPublishedDateTime(@Nullable Date relativeDate);

}
