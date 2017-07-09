package com.diegocast.twitterapp.domain;

import com.diegocast.twitterapp.domain.model.Response;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import rx.Observable;

public interface UserRepository {
    Observable<Response<Void, Boolean>> login(TwitterSession session);
    void logout();
    Observable<Response<Void, Boolean>> isUserAuthenticated();
    Observable<Response<User, Boolean>> self();
    Observable<Response<List<Tweet>, Boolean>> feed();
}
