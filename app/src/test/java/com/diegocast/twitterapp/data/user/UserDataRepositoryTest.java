package com.diegocast.twitterapp.data.user;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.mockito.Mock;

public class UserDataRepositoryTest {

    private UserDataRepository repository;

    @Mock
    FirebaseAuth firebaseAuth;

    @Before
    public void setUp() {
        repository = new UserDataRepository(firebaseAuth);
    }


}