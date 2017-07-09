package com.diegocast.twitterapp.data.user;

import com.diegocast.twitterapp.data.feed.TwitterApiRepository;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.mockito.Mock;

public class UserDataRepositoryTest {

    private UserDataRepository repository;

    @Mock
    FirebaseAuth firebaseAuth;

    @Mock
    TwitterApiRepository apiRepository;

    @Before
    public void setUp() {
        repository = new UserDataRepository(apiRepository, firebaseAuth);
    }


}