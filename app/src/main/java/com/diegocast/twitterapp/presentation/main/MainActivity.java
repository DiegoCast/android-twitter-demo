package com.diegocast.twitterapp.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.diegocast.twitterapp.R;
import com.diegocast.twitterapp.presentation.base.DaggerActivity;
import com.diegocast.twitterapp.presentation.dependency.view.ViewModule;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends DaggerActivity implements MainView {

    @BindView(R.id.login_button)
    TwitterLoginButton loginButton;

    @BindView(R.id.progress)
    ContentLoadingProgressBar progressBar;

    @Inject
    MainPresenter presenter;

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Here we start the injection tree. Dagger will first generate necessary dependencies from
        //ApplicationComponent and downstream. We separate components in the hierarchy
        //and scope them for better resource management in the case of user, activity and view
        //dependencies independently. We use the ViewModule as a means to abstract presenter from
        //the view classes through injection (each presenter should cast its interface to his
        //associated view after injection).

        component()
                .plus(new ViewModule(this))
                .inject(this);
        ButterKnife.bind(this);
        presenter.create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showLogin() {
        progressBar.hide();
        loginButton.setVisibility(View.VISIBLE);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                presenter.login(result);
            }

            @Override
            public void failure(TwitterException exception) {
                presenter.loginFailure(exception);
            }
        });
    }

    @Override
    public void showLoginError(String message) {
        showError(message);
    }

    @Override
    public void showAuthenticationError() {
        showError(getString(R.string.authentication_error));
    }

    @Override
    public void close() {
        finish();
    }

    private void showError(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.error_title));
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}
