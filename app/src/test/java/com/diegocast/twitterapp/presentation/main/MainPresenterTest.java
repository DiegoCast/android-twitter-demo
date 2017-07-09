package com.diegocast.twitterapp.presentation.main;

import com.diegocast.twitterapp.domain.model.Response;
import com.diegocast.twitterapp.domain.usecase.IsUserAuthenticatedUseCase;
import com.diegocast.twitterapp.domain.usecase.LoginUseCase;
import com.diegocast.twitterapp.presentation.base.Navigator;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

    private MainPresenter presenter;

    @Mock
    MainView view;

    @Mock
    Navigator navigator;

    @Mock
    LoginUseCase loginUseCase;

    @Mock
    IsUserAuthenticatedUseCase isUserAuthenticatedUseCase;

    private MainPresenter.LoginSubscriber loginSubscriber;
    private MainPresenter.IsUserAuthenticatedSubscriber isUserAuthenticatedSubscriber;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenter(view, navigator, loginUseCase, isUserAuthenticatedUseCase,
                Schedulers.immediate(), Schedulers.immediate());

        loginSubscriber = presenter.new LoginSubscriber();
        isUserAuthenticatedSubscriber = presenter.new IsUserAuthenticatedSubscriber();
    }

    @Test
    public void createTest() {
        when(isUserAuthenticatedUseCase.isUserAuthenticated())
                .thenReturn(Observable.just(Response.create(null, true)));

        presenter.create();

        verify(isUserAuthenticatedUseCase).isUserAuthenticated();
    }

    @Test
    public void createUserNotAuthenticatedTest() {
        when(isUserAuthenticatedUseCase.isUserAuthenticated())
                .thenReturn(Observable.just(Response.create(null, false)));

        presenter.create();

        verify(view).showLogin();
    }

    @Test
    public void loginTest() {
        final TwitterAuthToken token = new TwitterAuthToken("token", "secret");
        final long userId = 123;
        final String username = "username";
        TwitterSession session = new TwitterSession(token, userId, username);

        when(loginUseCase.login(any())).thenReturn(Observable.empty());

        //We mock Response since we don't need it. Twitter api handles success and failure login
        //logic and we wouldn't need it for anything else:

        Result<TwitterSession> result = new Result<>(session, null);

        presenter.login(result);

        verify(loginUseCase).login(session);
    }

    @Test
    public void loginFailureTest() {
        final TwitterException exception = new TwitterException("error");

        presenter.loginFailure(exception);

        verify(view).showLoginError("error");
    }


    @Test
    public void isUserAuthenticatedSuccessTest() {
        // For subscription events we use different tests, as its good practise to separate the
        // subscription logic from the observer logic:
        isUserAuthenticatedSubscriber.onNext(Response.create(null, true));

        verify(navigator).navigateToFeed();
        verify(view).close();
    }

    @Test
    public void isUserAuthenticatedUnSuccessTest() {
        isUserAuthenticatedSubscriber.onNext(Response.create(null, false));

        verify(view).showLogin();
    }

    @Test
    public void authenticationSuccessTest() {
        loginSubscriber.onNext(Response.create(null, true));

        verify(navigator).navigateToFeed();
        verify(view).close();
    }

    @Test
    public void authenticationUnSuccessTest() {
        loginSubscriber.onNext(Response.create(null, false));

        verify(view).showAuthenticationError();
    }
}