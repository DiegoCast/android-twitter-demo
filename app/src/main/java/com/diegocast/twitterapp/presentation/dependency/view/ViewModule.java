package com.diegocast.twitterapp.presentation.dependency.view;


import com.diegocast.twitterapp.presentation.BaseView;

import dagger.Module;
import dagger.Provides;


@Module
public class ViewModule {
    private final BaseView baseView;

    public ViewModule(BaseView baseView) {
        this.baseView = baseView;
    }

    @Provides
    BaseView baseViewProvider() {
        return baseView;
    }
}
