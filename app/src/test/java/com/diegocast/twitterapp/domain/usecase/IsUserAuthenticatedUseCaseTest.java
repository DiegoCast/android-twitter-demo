package com.diegocast.twitterapp.domain.usecase;

import com.diegocast.twitterapp.domain.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class IsUserAuthenticatedUseCaseTest {
    @Mock
    UserRepository repository;

    private IsUserAuthenticatedUseCase useCase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        useCase = new IsUserAuthenticatedUseCase(repository);
    }

    @Test
    public void isUserAuthenticatedTest() {
        useCase.isUserAuthenticated();
        verify(repository).isUserAuthenticated();
    }
}