package org.zeniafrik.di;

import org.zeniafrik.ZeniAfrik;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ActivityBuilder.class,
        MainModule.class,
        AndroidSupportInjectionModule.class
})

public interface ApplicationComponent extends AndroidInjector<ZeniAfrik> {
    void inject(ZeniAfrik application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(android.app.Application application);

        ApplicationComponent build();
    }
}
