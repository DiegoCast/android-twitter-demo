package com.diegocast.twitterapp.presentation.dependency.application;

import android.content.Context;

import com.diegocast.twitterapp.App;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class ApplicationModule {
    private final App application;

    public ApplicationModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplication() {
        return this.application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext(App app) {
        return app.getApplicationContext();
    }

    @Provides
    @Named("main")
    static Scheduler schedulerMainProvider() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Named("computation")
    static Scheduler schedulerComputationProvider() {
        return Schedulers.computation();
    }
}
