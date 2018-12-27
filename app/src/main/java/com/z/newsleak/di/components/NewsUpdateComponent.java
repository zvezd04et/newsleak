package com.z.newsleak.di.components;

import com.z.newsleak.App;
import com.z.newsleak.di.modules.AppModule;
import com.z.newsleak.di.modules.NetworkModule;
import com.z.newsleak.di.modules.PersistenceModule;
import com.z.newsleak.di.scopes.NewsUpdateScope;
import com.z.newsleak.features.newsfeed.NewsListPresenter;
import com.z.newsleak.service.NewsUpdateService;

import dagger.Component;

@NewsUpdateScope
@Component(modules = {AppModule.class, NetworkModule.class, PersistenceModule.class})
public interface NewsUpdateComponent {

    void inject(App app);

    void inject(NewsListPresenter newsListPresenter);

    void inject(NewsUpdateService newsUpdateService);
}
