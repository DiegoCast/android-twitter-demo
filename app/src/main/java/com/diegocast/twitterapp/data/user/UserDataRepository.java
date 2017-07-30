package com.diegocast.twitterapp.data.user;

import com.diegocast.twitterapp.data.feed.TwitterApiRepository;
import com.diegocast.twitterapp.data.persistance.PersistanceRepository;
import com.diegocast.twitterapp.domain.UserRepository;
import com.diegocast.twitterapp.domain.model.Response;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class UserDataRepository implements UserRepository {

    private TwitterApiRepository twitterApiRepository;
    private PersistanceRepository persistanceRepository;

    @Inject
    public UserDataRepository(TwitterApiRepository twitterApiRepository,
                              PersistanceRepository persistanceRepository) {
        this.twitterApiRepository = twitterApiRepository;
        this.persistanceRepository = persistanceRepository;
    }

    @Override
    public Observable<Response<User, Boolean>> self() {
        return twitterApiRepository.getSelf();
    }

    @Override
    public Observable<Response<List<Tweet>, Boolean>> feed() {
        return twitterApiRepository.getHomeFeed();
    }

    @Override
    public Observable<Response<Void, Boolean>> favorite(Tweet tweet) {
        return persistanceRepository.save(tweet);
    }

    @Override
    public Observable<Response<List<Tweet>, Boolean>> favorites() {
        return persistanceRepository.favorites();
    }
}
