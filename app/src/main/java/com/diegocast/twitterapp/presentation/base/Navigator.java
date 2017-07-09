package com.diegocast.twitterapp.presentation.base;

import android.content.Context;
import android.content.Intent;

import com.diegocast.twitterapp.presentation.feed.FeedActivity;
import com.diegocast.twitterapp.presentation.main.MainActivity;

import javax.inject.Inject;

/**
 * Manages app's navigation. Each view is responsible for knowing how to create itself and this
 * class is the decoupled nexus that helps navigating through them supporting
 * the presentation layer.
 */
public class Navigator {

    private Context context;

    @Inject
    public Navigator (Context context) {
        this.context = context;
    }

    public void navigateToFeed() {
        Intent intent = FeedActivity.newInstance(context);
        context.startActivity(intent);
    }

    public void navigateToMain() {
        Intent intent = MainActivity.newInstance(context);
        context.startActivity(intent);
    }
}
