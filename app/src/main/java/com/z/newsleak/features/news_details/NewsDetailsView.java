package com.z.newsleak.features.news_details;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.z.newsleak.features.base.BaseNewsItemView;

public interface NewsDetailsView extends BaseNewsItemView {

    @StateStrategyType(SkipStrategy.class)
    void openEditorActivity(int newsId);

}