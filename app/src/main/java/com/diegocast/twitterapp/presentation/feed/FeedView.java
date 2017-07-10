package com.diegocast.twitterapp.presentation.feed;

import com.diegocast.twitterapp.presentation.BaseView;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

public interface FeedView extends BaseView {
    void showFeed(List<Tweet> tweets);
    void showUserInfo(String userName, String profileImageUrl, String backgroundImageUrl);
    void showRetry();
    void showFavoriteSaveError();
}
