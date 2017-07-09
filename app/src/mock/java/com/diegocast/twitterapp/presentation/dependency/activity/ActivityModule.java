package com.diegocast.twitterapp.presentation.dependency.activity;

import android.app.Activity;

import com.diegocast.twitterapp.presentation.base.Navigator;
import com.diegocast.twitterapp.presentation.feed.FeedPresenter;
import com.diegocast.twitterapp.presentation.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Mock module to mock all presenters here since they are needed because of injection
 * (the whole reason for mock folder)
 */
@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    MainPresenter mainPresenterProvider() {
        return mock(MainPresenter.class);
    }

    @Provides
    FeedPresenter feedPresenterProvider() {
        return mock(FeedPresenter.class);
    }

    @Provides
    Navigator navigatorProvider() {
        return mock(Navigator.class);
    }
}
