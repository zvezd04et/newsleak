package com.z.newsleak.di.components;

import com.z.newsleak.di.modules.NetworkModule;
import com.z.newsleak.di.scopes.NetworkScope;
import com.z.newsleak.features.newsfeed.NewsListPresenter;
import com.z.newsleak.service.NewsUpdateService;

import dagger.Component;

@NetworkScope
@Component(modules={NetworkModule.class})
public interface NetworkComponent {
    void inject(NewsListPresenter newsListPresenter);

    void inject(NewsUpdateService newsUpdateService);
}
