package com.diegocast.twitterapp.domain.usecase;

import com.diegocast.twitterapp.domain.AuthRepository;
import com.diegocast.twitterapp.domain.model.Response;
import com.twitter.sdk.android.core.TwitterSession;

import javax.inject.Inject;

import rx.Observable;

/**
 * Updates token after validation.
 */
public class LoginUseCase {

    private AuthRepository repository;

    @Inject
    public LoginUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public Observable<Response<Void, Boolean>> login(TwitterSession session) {
        return repository.login(session);
    }
}
