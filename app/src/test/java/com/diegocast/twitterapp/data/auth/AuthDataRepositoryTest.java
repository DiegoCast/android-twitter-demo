package com.diegocast.twitterapp.data.auth;

import com.diegocast.twitterapp.domain.model.Response;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import rx.observers.TestSubscriber;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthDataRepositoryTest {

    @Mock
    FirebaseAuth firebaseAuth;

    private AuthDataRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        repository = new AuthDataRepository(firebaseAuth);
    }

    @Test
    public void loginTest() {
        //FIXME cannot find an easy way to test this since for the twitter / firebase auth
        // integration I had to use Observable.create as well as needing to create AuthCredential
        // within scope. Can't get needed mocks easily:
        TwitterAuthToken token = new TwitterAuthToken("token", "secret");
        TwitterSession session = new TwitterSession(token, 123, "userName");
        TestSubscriber<Response<Void, Boolean>> subscriber = new TestSubscriber<>();

        repository.login(session)
                .subscribe(subscriber);

        subscriber.assertReceivedOnNext(new ArrayList<>(Arrays
                .asList(Response.create(null, true))));
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }

    @Test
    public void logoutTest() {
        repository.logout();

        verify(firebaseAuth).signOut();
    }

    @Test
    public void isUserAuthenticatedTest() {
        FirebaseUser firebaseUser = mock(FirebaseUser.class);
        TestSubscriber<Response<Void, Boolean>> subscriber = new TestSubscriber<>();

        when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);

        repository.isUserAuthenticated()
                .subscribe(subscriber);

        subscriber.assertReceivedOnNext(new ArrayList<>(Arrays
                .asList(Response.create(null, true))));
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }

    @Test
    public void isUserUnAuthenticatedTest() {
        TestSubscriber<Response<Void, Boolean>> subscriber = new TestSubscriber<>();

        when(firebaseAuth.getCurrentUser()).thenReturn(null);

        repository.isUserAuthenticated()
                .subscribe(subscriber);

        subscriber.assertReceivedOnNext(new ArrayList<>(Arrays
                .asList(Response.create(null, false))));
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }
}