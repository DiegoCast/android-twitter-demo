package com.diegocast.twitterapp.presentation.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.diegocast.twitterapp.R;
import com.diegocast.twitterapp.presentation.base.DaggerActivity;
import com.diegocast.twitterapp.presentation.dependency.view.ViewModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedActivity extends DaggerActivity implements FeedView,
        Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    FeedPresenter presenter;

    public static Intent newInstance(Context context) {
        return new Intent(context, FeedActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        component()
                .plus(new ViewModule(this))
                .inject(this);
        ButterKnife.bind(this);


        toolbar.setOnMenuItemClickListener(this);
        toolbar.inflateMenu(R.menu.feed);

        presenter.create();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.feed_logout:
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_exit_to_app_white_24dp)
                        .setTitle(getString(R.string.feed_logout))
                        .setMessage(getString(R.string.feed_logout_description))
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                            presenter.logout();
                            close();
                        })
                        .setNegativeButton(getString(R.string.no), null)
                        .show();
                return true;
        }
        return false;
    }

    @Override
    public void close() {
        finish();
    }
}
