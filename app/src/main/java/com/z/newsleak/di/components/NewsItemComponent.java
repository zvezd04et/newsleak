package com.z.newsleak.di.components;

import com.z.newsleak.di.modules.NewsItemModule;
import com.z.newsleak.di.scopes.NewsItemScope;
import com.z.newsleak.features.news_details.NewsDetailsFragment;
import com.z.newsleak.features.news_edit.NewsEditActivity;

import dagger.Subcomponent;

@NewsItemScope
@Subcomponent(modules = NewsItemModule.class)
public interface NewsItemComponent {

    void inject(NewsDetailsFragment newsDetailsFragment);

    void inject(NewsEditActivity newsEditActivity);
}
