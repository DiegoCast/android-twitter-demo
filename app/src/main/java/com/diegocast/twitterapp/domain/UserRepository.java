package com.diegocast.twitterapp.domain;

import com.diegocast.twitterapp.domain.model.Response;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import rx.Observable;

public interface UserRepository {
    Observable<Response<User, Boolean>> self();
    Observable<Response<List<Tweet>, Boolean>> feed();
    Observable<Response<Void, Boolean>> favorite(Tweet tweet);
    Observable<Response<List<Tweet>, Boolean>> favorites();
}
