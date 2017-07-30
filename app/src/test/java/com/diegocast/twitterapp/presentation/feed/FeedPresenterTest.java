package com.diegocast.twitterapp.presentation.feed;

import com.diegocast.twitterapp.Models;
import com.diegocast.twitterapp.domain.model.Response;
import com.diegocast.twitterapp.domain.model.User;
import com.diegocast.twitterapp.domain.usecase.GetFeedUseCase;
import com.diegocast.twitterapp.domain.usecase.GetSelfUseCase;
import com.diegocast.twitterapp.domain.usecase.LogoutUseCase;
import com.diegocast.twitterapp.domain.usecase.SaveTweetUseCase;
import com.diegocast.twitterapp.presentation.base.Navigator;
import com.twitter.sdk.android.core.models.Tweet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class FeedPresenterTest {

    private FeedPresenter presenter;

    @Mock
    FeedView view;

    @Mock
    Navigator navigator;

    @Mock
    LogoutUseCase logoutUseCase;

    @Mock
    GetFeedUseCase getFeedUseCase;

    @Mock
    GetSelfUseCase getSelfUseCase;

    @Mock
    SaveTweetUseCase saveTweetUseCase;

    private FeedPresenter.GetFeedSubscriber getFeedSubscriber;
    private FeedPresenter.GetSelfSubscriber getSelfSubscriber;
    private FeedPresenter.SaveTweetSubscriber saveTweetSubscriber;
    private User self;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new FeedPresenter(view, navigator, logoutUseCase, getFeedUseCase,
                getSelfUseCase, saveTweetUseCase, Schedulers.immediate(), Schedulers.immediate());

        getFeedSubscriber = presenter.new GetFeedSubscriber();
        getSelfSubscriber = presenter.new GetSelfSubscriber();
        saveTweetSubscriber = presenter.new SaveTweetSubscriber();
        self = Models.user(123L, "screenName", "profileImageUrl", "profileBannerUrl");
    }

    @Test
    public void createTest() {
        when(getSelfUseCase.self())
                .thenReturn(Observable.just(Response.create(self, true)));
        when(getFeedUseCase.feed())
                .thenReturn(Observable.just(Response.create(null, true)));

        presenter.create(null);

        verify(getSelfUseCase).self();
        verify(getFeedUseCase).feed();
    }

    @Test
    public void logoutTest() {
        presenter.logout();

        verify(logoutUseCase).logout();
        verify(navigator).navigateToMain();
        verify(view).close();
    }

    @Test
    public void saveFavoriteTweetTest() {
        Tweet tweet = Models.tweet(123L);
        when(saveTweetUseCase.favorite(tweet))
                .thenReturn(Observable.just(Response.create(null, true)));

        presenter.saveFavoriteTweet(tweet);

        verify(saveTweetUseCase).favorite(tweet);
    }

    @Test
    public void getSelfSubscriberSuccessTest() {
        getSelfSubscriber.onNext(Response.create(self, true));

        verify(view).showUserInfo("screenName", "profileImageUrl", "profileBannerUrl");
    }

    @Test
    public void getSelfSubscriberUnSuccessTest() {
        getSelfSubscriber.onNext(Response.create(null, false));

        verify(view).showRetry();
    }

    @Test
    public void getFeedSubscriberSuccessTest() {
        final List<Tweet> tweets = new ArrayList<>();
        getFeedSubscriber.onNext(Response.create(tweets, true));

        verify(view).showFeed(tweets);
    }

    @Test
    public void getFeedSubscriberUnSuccessTest() {
        getFeedSubscriber.onNext(Response.create(null, false));

        verify(view).showRetry();
    }

    @Test
    public void saveTweetSubscriberSuccessTest() {
        saveTweetSubscriber.onNext(Response.create(null, true));

        verifyNoMoreInteractions(view);
    }

    @Test
    public void saveTweetSubscriberUnSuccessTest() {
        saveTweetSubscriber.onNext(Response.create(null, false));

        verify(view).showFavoriteSaveError();
    }
}