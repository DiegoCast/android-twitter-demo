package com.diegocast.twitterapp.presentation.dependency;

import com.diegocast.twitterapp.data.dependency.UserDataModule;
import com.diegocast.twitterapp.presentation.dependency.activity.ActivityComponent;
import com.diegocast.twitterapp.presentation.dependency.activity.ActivityModule;

import dagger.Subcomponent;

@UserScope
@Subcomponent(modules = {
        UserDataModule.class,
})
public interface UserComponent {

    @Subcomponent.Builder
    public interface Builder {
        UserComponent build();
    }

    ActivityComponent plus(ActivityModule activityModule);
}
