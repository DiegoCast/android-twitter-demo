package com.diegocast.twitterapp.data.dependency;

import com.diegocast.twitterapp.data.auth.AuthDataRepository;
import com.diegocast.twitterapp.data.feed.TwitterApiRepository;
import com.diegocast.twitterapp.data.persistance.PersistanceRepository;
import com.diegocast.twitterapp.data.user.UserDataRepository;
import com.diegocast.twitterapp.domain.AuthRepository;
import com.diegocast.twitterapp.domain.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

@Module
public class UserDataModule {

    @Provides
    UserRepository userRepositoryProvider(TwitterApiRepository apiRepository,
                                          PersistanceRepository persistanceRepository) {
        return new UserDataRepository(apiRepository, persistanceRepository);
    }

    @Provides
    AuthRepository authRepositoryProvider(FirebaseAuth firebaseAuth) {
        return new AuthDataRepository(firebaseAuth);
    }
}
