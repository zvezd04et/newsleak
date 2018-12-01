package com.z.newsleak.features.news_edit;

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.z.newsleak.features.base.BaseNewsItemView;

import java.util.Calendar;

import androidx.annotation.NonNull;

public interface NewsEditView extends BaseNewsItemView {

    @StateStrategyType(SingleStateStrategy.class)
    void setCalendar(@NonNull Calendar calendar);

}
