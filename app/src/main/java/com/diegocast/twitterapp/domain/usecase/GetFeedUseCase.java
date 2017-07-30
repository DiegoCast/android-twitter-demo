package com.diegocast.twitterapp.domain.usecase;

import com.diegocast.twitterapp.domain.UserRepository;
import com.diegocast.twitterapp.domain.model.Response;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetFeedUseCase {

    UserRepository repository;

    @Inject
    public GetFeedUseCase (UserRepository repository) {
        this.repository = repository;
    }

    public Observable<Response<List<Tweet>, Boolean>> feed() {
        return repository.feed();
    }

    public Observable<Response<List<Tweet>, Boolean>> userFeed(long userId, String screenName) {
        return repository.userFeed(userId, screenName);
    }
}
