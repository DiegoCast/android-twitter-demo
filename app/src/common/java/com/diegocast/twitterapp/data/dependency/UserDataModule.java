package com.diegocast.twitterapp.data.dependency;

import com.diegocast.twitterapp.data.feed.TwitterApiRepository;
import com.diegocast.twitterapp.data.persistance.PersistanceRepository;
import com.diegocast.twitterapp.data.user.UserDataRepository;
import com.diegocast.twitterapp.domain.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

@Module
public class UserDataModule {

    @Provides
    UserRepository userRepositoryProvider(TwitterApiRepository apiRepository,
                                          PersistanceRepository persistanceRepository,
                                          FirebaseAuth firebaseAuth) {
        return new UserDataRepository(apiRepository, persistanceRepository, firebaseAuth);
    }
}
