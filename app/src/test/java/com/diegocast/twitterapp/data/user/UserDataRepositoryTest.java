package com.diegocast.twitterapp.data.user;

import com.diegocast.twitterapp.Models;
import com.diegocast.twitterapp.data.feed.TwitterApiRepository;
import com.diegocast.twitterapp.data.persistance.PersistanceRepository;
import com.diegocast.twitterapp.domain.model.Response;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserDataRepositoryTest {

    private UserDataRepository repository;

    @Mock
    FirebaseAuth firebaseAuth;

    @Mock
    TwitterApiRepository apiRepository;

    @Mock
    PersistanceRepository persistanceRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        repository = new UserDataRepository(apiRepository, persistanceRepository, firebaseAuth);
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

    @Test
    public void selfTest() {
        User self = Models.self("screenName", "profileImageUrl", "profileBannerUrl");
        when(apiRepository.getSelf()).thenReturn(Observable.just(Response.create(self, true)));
        repository.self();

        verify(apiRepository).getSelf();
    }

    @Test
    public void feedTest() {
        List<Tweet> tweets = new ArrayList<>();
        when(apiRepository.getHomeFeed())
                .thenReturn(Observable.just(Response.create(tweets, true)));

        repository.feed();

        verify(apiRepository).getHomeFeed();
    }

    @Test
    public void favoriteTest() {
        Tweet tweet = Models.tweet(123L);
        when(persistanceRepository.save(tweet))
                .thenReturn(Observable.just(Response.create(null, true)));

        repository.favorite(tweet);

        verify(persistanceRepository).save(tweet);
    }

    @Test
    public void favoritesTest() {
        List<Tweet> tweets = new ArrayList<>();
        when(persistanceRepository.favorites())
                .thenReturn(Observable.just(Response.create(tweets, true)));
        repository.favorites();

        verify(persistanceRepository).favorites();
    }
}