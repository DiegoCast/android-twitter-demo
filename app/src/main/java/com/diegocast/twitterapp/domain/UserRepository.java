package com.diegocast.twitterapp.domain;

import com.diegocast.twitterapp.domain.model.Response;
import com.twitter.sdk.android.core.TwitterSession;

import rx.Observable;

public interface UserRepository {
    Observable<Response<Void, Boolean>> login(TwitterSession session);
    void logout();
    Observable<Response<Void, Boolean>> isUserAuthenticated();
}
