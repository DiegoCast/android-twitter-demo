package com.diegocast.twitterapp.data.dependency;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    FirebaseAuth firebaseAuthProvider() {
        return FirebaseAuth.getInstance();
    }
}
