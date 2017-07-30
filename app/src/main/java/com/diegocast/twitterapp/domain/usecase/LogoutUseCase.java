package com.diegocast.twitterapp.domain.usecase;

import com.diegocast.twitterapp.domain.AuthRepository;

import javax.inject.Inject;

public class LogoutUseCase {

    private AuthRepository repository;

    @Inject
    public LogoutUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public void logout() {
        repository.logout();
    }
}
