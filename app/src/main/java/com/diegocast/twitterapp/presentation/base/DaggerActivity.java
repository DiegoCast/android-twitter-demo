package com.diegocast.twitterapp.presentation.base;


import android.support.v7.app.AppCompatActivity;

import com.diegocast.twitterapp.App;
import com.diegocast.twitterapp.presentation.dependency.activity.ActivityComponent;
import com.diegocast.twitterapp.presentation.dependency.activity.ActivityInjector;
import com.diegocast.twitterapp.presentation.dependency.activity.ActivityModule;

/**
 * Wrapper class to abstract injection thorough activity component.
 */
public class DaggerActivity extends AppCompatActivity implements ActivityInjector {
    private ActivityComponent component;

    @Override
    public ActivityComponent component() {
        if (component == null) {
            component = App.get(this).getUserComponent()
                    .plus(new ActivityModule(this));
        }
        return component;
    }
}
