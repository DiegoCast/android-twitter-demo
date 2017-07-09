package com.diegocast.twitterapp.domain.usecase;

import com.diegocast.twitterapp.domain.UserRepository;

import javax.inject.Inject;

public class LogoutUseCase {

    private UserRepository repository;

    @Inject
    public LogoutUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void logout() {
        repository.logout();
    }
}
