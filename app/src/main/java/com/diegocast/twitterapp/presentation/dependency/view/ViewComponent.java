package com.diegocast.twitterapp.presentation.dependency.view;

import com.diegocast.twitterapp.presentation.feed.FeedActivity;
import com.diegocast.twitterapp.presentation.main.MainActivity;

import dagger.Subcomponent;

@ViewScope
@Subcomponent(modules = {
        ViewModule.class,
})
public interface ViewComponent {

    void inject(MainActivity activity);
    void inject(FeedActivity feedActivity);
}
