package com.diegocast.twitterapp.presentation.main;

import com.diegocast.twitterapp.presentation.BaseView;

public interface MainView extends BaseView {
    void showLogin();
    void showLoginError(String message);
    void showAuthenticationError();
}
