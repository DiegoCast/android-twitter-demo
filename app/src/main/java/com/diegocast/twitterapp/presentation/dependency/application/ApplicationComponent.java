package com.diegocast.twitterapp.presentation.dependency.application;

import com.diegocast.twitterapp.data.dependency.DataModule;
import com.diegocast.twitterapp.domain.dependency.DomainModule;
import com.diegocast.twitterapp.presentation.dependency.UserComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DomainModule.class, DataModule.class})
public interface ApplicationComponent {
//    GetSystemInfoUseCase getCredentialsUseCase();
//
//    TokenAuthenticator tokenAuthenticator();
//
    UserComponent.Builder userComponentBuilder();
//
//    ExceptionLogger exceptionLogger();
}
