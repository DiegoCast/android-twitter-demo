package com.diegocast.twitterapp.data.feed;

import com.diegocast.twitterapp.domain.model.Response;
import com.diegocast.twitterapp.domain.model.User;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * This class is responsible to call for and wrap/parse rest api responses into data models.
 * In the case of this demo we use the same model (Tweet, User) throughout the application because
 * of its inbuilt support and integration with twitter's android api in the form of widgets and
 * components. At the same time, we rely on Retrofit type adapter to parse the json response and
 * turn it into an observable
 */
public class TwitterApiRepository {

    private TwitterRestApi restApi;

    @Inject
    public TwitterApiRepository(TwitterRestApi restApi) {
        this.restApi = restApi;
    }

    public Observable<Response<User, Boolean>> getSelf() {
        return restApi.verifyCredentials(true, false, false)
                .map(new Func1<retrofit2.Response<com.twitter.sdk.android.core.models.User>, Response<User, Boolean>>() {
            @Override
            public Response<User, Boolean> call(retrofit2.Response<com.twitter.sdk.android.core.models.User> response) {
                if (response.isSuccessful()) {
                    final com.twitter.sdk.android.core.models.User body = response.body();
                    return Response.create(User.create(
                            body.id,
                            body.screenName,
                            body.profileImageUrl.replace("_normal",""),
                            body.profileBannerUrl), true);
                } else {
                    // We don't care about data if the request failed, we just push the state
                    // forward for business logic to deal with:
                    return Response.create(null, false);
                }
            }
        });
    }

    public Observable<Response<List<Tweet>, Boolean>> getHomeFeed() {
        return restApi.homeTimeline(null, null, null, null, true, null, true)
                .map(new Func1<retrofit2.Response<List<Tweet>>, Response<List<Tweet>, Boolean>>() {
                    @Override
                    public Response<List<Tweet>, Boolean> call(retrofit2.Response<List<Tweet>> response) {
                        if (response.isSuccessful()) {
                            return Response.create(response.body(), true);
                        } else {
                            return Response.create(null, false);
                        }
                    }
                });
    }

    public Observable<Response<List<Tweet>, Boolean>> getUserFeed(long userId, String screenName) {
        return restApi.userTimeline(userId, screenName, null, null, null, false, null, true, true)
                .map(new Func1<retrofit2.Response<List<Tweet>>, Response<List<Tweet>, Boolean>>() {
                    @Override
                    public Response<List<Tweet>, Boolean> call(retrofit2.Response<List<Tweet>> response) {
                        if (response.isSuccessful()) {
                            return Response.create(response.body(), true);
                        } else {
                            return Response.create(null, false);
                        }
                    }
                });
    }
}
