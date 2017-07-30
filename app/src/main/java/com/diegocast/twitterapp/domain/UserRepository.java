package com.diegocast.twitterapp.domain;

import com.diegocast.twitterapp.domain.model.Response;
import com.diegocast.twitterapp.domain.model.User;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import rx.Observable;

public interface UserRepository {
    Observable<Response<User, Boolean>> self();
    Observable<Response<List<Tweet>, Boolean>> feed();
    Observable<Response<List<Tweet>, Boolean>> userFeed(long userId, String screenName);
    Observable<Response<Void, Boolean>> favorite(Tweet tweet);
    Observable<Response<List<Tweet>, Boolean>> favorites();
}
