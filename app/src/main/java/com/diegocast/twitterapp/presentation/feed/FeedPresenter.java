package com.diegocast.twitterapp.presentation.feed;

import com.diegocast.twitterapp.domain.model.Response;
import com.diegocast.twitterapp.domain.usecase.GetFeedUseCase;
import com.diegocast.twitterapp.domain.usecase.GetSelfUseCase;
import com.diegocast.twitterapp.domain.usecase.LogoutUseCase;
import com.diegocast.twitterapp.domain.usecase.SaveTweetUseCase;
import com.diegocast.twitterapp.presentation.BaseView;
import com.diegocast.twitterapp.presentation.base.Navigator;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;

public class FeedPresenter {

    private final FeedView view;
    private Navigator navigator;
    private LogoutUseCase logoutUseCase;
    private GetFeedUseCase getFeedUseCase;
    private GetSelfUseCase getSelfUseCase;
    private SaveTweetUseCase saveTweetUseCase;
    private Scheduler computation;
    private Scheduler main;
    private Subscription getSelfSubscription;
    private Subscription getFeedSubscription;
    private Subscription saveTweetSubscription;

    @Inject
    public FeedPresenter (BaseView view, Navigator navigator, LogoutUseCase logoutUseCase,
                          GetFeedUseCase getFeedUseCase, GetSelfUseCase getSelfUseCase,
                          SaveTweetUseCase saveTweetUseCase,
                          @Named("computation") Scheduler computation,
                          @Named("main") Scheduler main) {
        this.view = (FeedView) view;
        this.navigator = navigator;
        this.logoutUseCase = logoutUseCase;
        this.getFeedUseCase = getFeedUseCase;
        this.getSelfUseCase = getSelfUseCase;
        this.saveTweetUseCase = saveTweetUseCase;
        this.computation = computation;
        this.main = main;
    }

    public void create() {
        getSelfSubscription = getSelfUseCase.self()
                .subscribeOn(computation)
                .observeOn(main)
                .subscribe(new GetSelfSubscriber());
        getFeedSubscription = getFeedUseCase.feed()
                .subscribeOn(computation)
                .observeOn(main)
                .subscribe(new GetFeedSubscriber());
    }

    public void logout() {
        logoutUseCase.logout();
        navigator.navigateToMain();
        view.close();
    }

    public void destroy() {
        getSelfSubscription.unsubscribe();
        getFeedSubscription.unsubscribe();
        if (saveTweetSubscription != null){
            saveTweetSubscription.unsubscribe();
        }
    }

    public void saveFavoriteTweet(Tweet tweet) {
        //TODO 
//        saveTweetSubscription = saveTweetUseCase.favorite(tweet)
//                .subscribeOn(computation)
//                .observeOn(main)
//                .subscribe(new SaveTweetSubscriber());
    }

    /**
     * Subscriber that returns the user's data
     *
     */
    public class GetSelfSubscriber extends Subscriber<Response<User, Boolean>> {

        @Override
        public void onNext(Response<User, Boolean> response) {
            if (response.state()) {
                final User self = response.data();
                // Apparently this replace line needs to happen to avoid getting an overly
                // low quality image:
                final String profileImageUrlNormal = self.profileImageUrl.replace("_normal","");
                view.showUserInfo(self.screenName, profileImageUrlNormal,
                        self.profileBannerUrl);
            } else {
                view.showRetry();
            }
        }

        @Override
        public void onError(Throwable e) {
            throw new RuntimeException("GetFeedSubscriber error", e);
        }

        @Override
        public void onCompleted() {}

    }

    /**
     * Subscriber that returns the user home feed
     *
     */
    public class GetFeedSubscriber extends Subscriber<Response<List<Tweet>, Boolean>> {

        @Override
        public void onNext(Response<List<Tweet>, Boolean> response) {
            if (response.state()) {
                view.showFeed(response.data());
            } else {
                view.showRetry();
            }
        }

        @Override
        public void onError(Throwable e) {
            throw new RuntimeException("GetFeedSubscriber error", e);
        }

        @Override
        public void onCompleted() {}

    }


    /**
     * Subscriber which notifies about saving a tweet
     *
     */
    public class SaveTweetSubscriber extends Subscriber<Response<Void, Boolean>> {

        @Override
        public void onNext(Response<Void, Boolean> response) {
            if (!response.state()) {
                view.showFavoriteSaveError();
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
