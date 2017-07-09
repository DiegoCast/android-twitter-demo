package com.diegocast.twitterapp.presentation.feed;

import com.diegocast.twitterapp.domain.usecase.LogoutUseCase;
import com.diegocast.twitterapp.presentation.BaseView;
import com.diegocast.twitterapp.presentation.base.Navigator;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Scheduler;

public class FeedPresenter {

    private final FeedView view;
    private Navigator navigator;
    private LogoutUseCase logoutUseCase;
    private Scheduler computation;
    private Scheduler main;

    @Inject
    public FeedPresenter (BaseView view, Navigator navigator, LogoutUseCase logoutUseCase,
                          @Named("computation") Scheduler computation,
                          @Named("main") Scheduler main) {
        this.view = (FeedView) view;
        this.navigator = navigator;
        this.logoutUseCase = logoutUseCase;
        this.computation = computation;
        this.main = main;
    }

    public void create() {

    }

    public void logout() {
        logoutUseCase.logout();
        navigator.navigateToMain();
    }
}
