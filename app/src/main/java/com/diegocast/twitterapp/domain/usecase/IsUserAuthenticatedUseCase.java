package com.diegocast.twitterapp.domain.usecase;

import com.diegocast.twitterapp.domain.UserRepository;
import com.diegocast.twitterapp.domain.model.Response;

import javax.inject.Inject;

import rx.Observable;

/**
 * Asks repository if there is currently a valid user authenticated
 */
public class IsUserAuthenticatedUseCase {

    private UserRepository repository;

    @Inject
    public IsUserAuthenticatedUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public Observable<Response<Void, Boolean>> isUserAuthenticated() {
        return repository.isUserAuthenticated();
    }
}
