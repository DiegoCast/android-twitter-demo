package com.diegocast.twitterapp.domain.usecase;

import com.diegocast.twitterapp.domain.AuthRepository;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class LoginUseCaseTest {

    @Mock
    AuthRepository repository;

    private LoginUseCase useCase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        useCase = new LoginUseCase(repository);
    }

    @Test
    public void loginTest() {
        final TwitterAuthToken token = new TwitterAuthToken("token", "secret");
        final long userId = 123;
        final String username = "username";
        TwitterSession session = new TwitterSession(token, userId, username);

        useCase.login(session);
        
        verify(repository).login(session);
    }
}