package com.diegocast.twitterapp;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.diegocast.twitterapp.presentation.dependency.UserComponent;
import com.diegocast.twitterapp.presentation.dependency.application.ApplicationComponent;
import com.diegocast.twitterapp.presentation.dependency.application.ApplicationModule;
import com.diegocast.twitterapp.presentation.dependency.application.DaggerApplicationComponent;
import com.google.firebase.FirebaseApp;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Twitter;

public class App extends MultiDexApplication {

    private ApplicationComponent applicationComponent;
    private UserComponent userComponent;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Picasso
        Picasso.Builder picassoBuilder = new Picasso.Builder(this);
        if (BuildConfig.DEBUG) {
            picassoBuilder.indicatorsEnabled(true);
        }
        Picasso.setSingletonInstance(picassoBuilder.build());

        // Twitter
        Twitter.initialize(this);

        // Firebase
        FirebaseApp.initializeApp(this);
    }

    public ApplicationComponent getApplicationComponent() {
        if (this.applicationComponent == null) {
            this.applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return this.applicationComponent;
    }

    public UserComponent getUserComponent() {
        if (this.userComponent == null) {
            this.userComponent = getApplicationComponent()
                    .userComponentBuilder()
                    .build();
        }
        return this.userComponent;
    }
}
