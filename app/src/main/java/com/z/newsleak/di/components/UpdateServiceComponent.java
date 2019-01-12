package com.z.newsleak.di.components;

import com.z.newsleak.di.modules.UpdateServiceModule;
import com.z.newsleak.di.scopes.UpdateServiceScope;
import com.z.newsleak.service.NewsUpdateService;

import dagger.Subcomponent;

@UpdateServiceScope
@Subcomponent(modules = UpdateServiceModule.class)
public interface UpdateServiceComponent {

    void inject(NewsUpdateService newsUpdateService);
}
