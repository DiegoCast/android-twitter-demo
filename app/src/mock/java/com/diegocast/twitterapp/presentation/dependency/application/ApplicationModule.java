package com.diegocast.twitterapp.presentation.dependency.application;

import com.diegocast.twitterapp.App;

import dagger.Module;

@Module
public class ApplicationModule {

    private final App application;

    public ApplicationModule(App application) {
        this.application = application;
    }
}
