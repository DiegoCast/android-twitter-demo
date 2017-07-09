package com.diegocast.twitterapp.domain.usecase;

import com.diegocast.twitterapp.domain.UserRepository;
import com.diegocast.twitterapp.domain.model.Response;
import com.twitter.sdk.android.core.TwitterSession;

import javax.inject.Inject;

import rx.Observable;

/**
 * Updates token after validation.
 */
public class LoginUseCase {

    private UserRepository repository;

    @Inject
    public LoginUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public Observable<Response<Void, Boolean>> login(TwitterSession session) {
        return repository.login(session);
    }
}
