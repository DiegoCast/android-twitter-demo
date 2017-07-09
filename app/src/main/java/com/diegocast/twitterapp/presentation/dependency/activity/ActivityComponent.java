package com.diegocast.twitterapp.presentation.dependency.activity;

import com.diegocast.twitterapp.presentation.dependency.view.ViewComponent;
import com.diegocast.twitterapp.presentation.dependency.view.ViewModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
        ActivityModule.class,
})
public interface ActivityComponent {

    ViewComponent plus(ViewModule viewModule);
}
