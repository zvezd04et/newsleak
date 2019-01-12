package com.z.newsleak.di.components;

import com.z.newsleak.App;
import com.z.newsleak.di.modules.AppModule;
import com.z.newsleak.di.modules.DbModule;
import com.z.newsleak.di.modules.NetworkModule;
import com.z.newsleak.di.modules.NewsItemModule;
import com.z.newsleak.di.modules.PreferencesModule;
import com.z.newsleak.di.modules.UpdateServiceModule;
import com.z.newsleak.features.intro.IntroActivity;
import com.z.newsleak.features.newsfeed.NewsListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                NetworkModule.class,
                DbModule.class,
                PreferencesModule.class
        }
)
public interface AppComponent {

    void inject(App app);

    void inject(NewsListFragment newsListFragment);

    void inject(IntroActivity introActivity);

    NewsItemComponent plus(NewsItemModule module);

    UpdateServiceComponent plus(UpdateServiceModule module);
}
