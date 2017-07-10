package com.diegocast.twitterapp.domain.usecase;

import com.diegocast.twitterapp.domain.UserRepository;
import com.diegocast.twitterapp.domain.model.Response;
import com.twitter.sdk.android.core.models.Tweet;

import javax.inject.Inject;

import rx.Observable;

public class SaveTweetUseCase {

    private UserRepository repository;

    @Inject
    public SaveTweetUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public Observable<Response<Void, Boolean>> favorite(Tweet tweet) {
        return repository.favorite(tweet);
    }
}
