package com.diegocast.twitterapp.presentation.dependency.activity;

import android.app.Activity;
import android.content.Context;

import com.diegocast.twitterapp.presentation.base.Navigator;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity activityProvider() {
        return activity;
    }

    @Provides
    Navigator navigatorProvider(Context context) {
        return new Navigator(context);
    }
}
