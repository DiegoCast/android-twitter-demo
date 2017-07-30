package com.diegocast.twitterapp.data.user;

import com.diegocast.twitterapp.Models;
import com.diegocast.twitterapp.data.feed.TwitterApiRepository;
import com.diegocast.twitterapp.data.persistance.PersistanceRepository;
import com.diegocast.twitterapp.domain.model.Response;
import com.diegocast.twitterapp.domain.model.User;
import com.twitter.sdk.android.core.models.Tweet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserDataRepositoryTest {

    private UserDataRepository repository;

    @Mock
    TwitterApiRepository apiRepository;

    @Mock
    PersistanceRepository persistanceRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        repository = new UserDataRepository(apiRepository, persistanceRepository);
    }

    @Test
    public void selfTest() {
        User self = Models.user(123L,"screenName", "profileImageUrl", "profileBannerUrl");
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