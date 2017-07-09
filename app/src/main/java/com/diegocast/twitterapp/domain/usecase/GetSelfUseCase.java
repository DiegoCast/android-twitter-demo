package com.diegocast.twitterapp.domain.usecase;

import com.diegocast.twitterapp.domain.UserRepository;
import com.diegocast.twitterapp.domain.model.Response;
import com.twitter.sdk.android.core.models.User;

import javax.inject.Inject;

import rx.Observable;

public class GetSelfUseCase {

    UserRepository repository;

    @Inject
    public GetSelfUseCase (UserRepository repository) {
        this.repository = repository;
    }

    public Observable<Response<User, Boolean>> self() {
        return repository.self();
    }
}
