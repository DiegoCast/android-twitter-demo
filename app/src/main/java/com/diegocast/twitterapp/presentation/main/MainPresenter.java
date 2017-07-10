package com.diegocast.twitterapp.presentation.main;

import com.diegocast.twitterapp.domain.model.Response;
import com.diegocast.twitterapp.domain.usecase.IsUserAuthenticatedUseCase;
import com.diegocast.twitterapp.domain.usecase.LoginUseCase;
import com.diegocast.twitterapp.presentation.BaseView;
import com.diegocast.twitterapp.presentation.base.Navigator;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Scheduler;
import rx.Subscriber;

public class MainPresenter {

    private final MainView view;
    private final Navigator navigator;
    private LoginUseCase loginUseCase;
    private IsUserAuthenticatedUseCase isUserAuthenticatedUseCase;
    private Scheduler computation;
    private Scheduler main;

    @Inject
    public MainPresenter(BaseView view, Navigator navigator, LoginUseCase loginUseCase,
                         IsUserAuthenticatedUseCase isUserAuthenticatedUseCase,
                         @Named("computation") Scheduler computation,
                         @Named("main") Scheduler main) {
        this.view = (MainView) view;
        this.navigator = navigator;
        this.loginUseCase = loginUseCase;
        this.isUserAuthenticatedUseCase = isUserAuthenticatedUseCase;
        this.computation = computation;
        this.main = main;
    }

    public void create() {
        // For the subscription we specify a computation thread to process all the channel data
        // transmission and background processing. We observe on the main thread as it is required
        // by the Android framework whenever we are interacting upon views:
        isUserAuthenticatedUseCase.isUserAuthenticated()
                .subscribeOn(computation)
                .observeOn(main)
                .subscribe(new IsUserAuthenticatedSubscriber());
    }

    public void loginFailure(TwitterException exception) {
        view.showLoginError(exception.getMessage());
    }

    public void login(Result<TwitterSession> result) {
        loginUseCase.login(result.data)
                .subscribeOn(computation)
                .observeOn(main)
                .subscribe(new LoginSubscriber());

    }

    /**
     * Subscriber which will return the results of OAuth authentication
     *
     */
    public class LoginSubscriber extends Subscriber<Response<Void, Boolean>> {

        @Override
        public void onNext(Response<Void, Boolean> response) {
            if (response.state()) {
                navigator.navigateToFeed();
                view.close();
            } else {
                view.showAuthenticationError();
            }
        }

        @Override
        public void onError(Throwable e) {
            throw new RuntimeException("LoginSubscriber error", e);
        }

        @Override
        public void onCompleted() {}

    }

    /**
     * Subscriber which will return wether the user is authenticated or not
     *
     */
    public class IsUserAuthenticatedSubscriber extends Subscriber<Response<Void, Boolean>> {

        @Override
        public void onNext(Response<Void, Boolean> response) {
            if (response.state()) {
                navigator.navigateToFeed();
                view.close();
            } else {
                view.showLogin();
            }
        }

        @Override
        public void onError(Throwable e) {
            throw new RuntimeException("IsUserAuthenticatedSubscriber error", e);
        }

        @Override
        public void onCompleted() {}

    }
}
