package com.diegocast.twitterapp.data.dependency;

import com.diegocast.twitterapp.Constants;
import com.diegocast.twitterapp.data.feed.TwitterRestApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.network.OAuth1aInterceptor;
import com.twitter.sdk.android.core.models.BindingValues;
import com.twitter.sdk.android.core.models.BindingValuesAdapter;
import com.twitter.sdk.android.core.models.SafeListAdapter;
import com.twitter.sdk.android.core.models.SafeMapAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

import static com.twitter.sdk.android.core.internal.network.OkHttpClientHelper.getCertificatePinner;

@Module
public class DataModule {

    @Provides
    @Singleton
    static Retrofit retrofitProvider(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(Constants.APIURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                // I adapted all httpclient and api twitter creation for this next line :)
                .addCallAdapterFactory(RxJavaCallAdapterFactory
                        .createWithScheduler(Schedulers.io()))
                .build();
    }

    @Provides
    @Singleton
    static FirebaseAuth firebaseAuthProvider() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    static Gson gsonProvider() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new SafeListAdapter())
                .registerTypeAdapterFactory(new SafeMapAdapter())
                .registerTypeAdapter(BindingValues.class, new BindingValuesAdapter())
                .create();
    }

    @Provides
    @Singleton
    static OkHttpClient okHttpClientProvider(TwitterSession session) {
        return new OkHttpClient.Builder()
                .certificatePinner(getCertificatePinner())
                .addInterceptor(new OAuth1aInterceptor(session,
                        Twitter.getInstance().getTwitterAuthConfig()))
                .build();
    }

    @Provides
    @Singleton
    static TwitterSession twitterSessionProvider() {
        //We have to assume at this stage that session is not null:
        return TwitterCore.getInstance().getSessionManager().getActiveSession();
    }

    @Provides
    @Singleton
    static TwitterRestApi twitterRestApiProvider(Retrofit retrofit) {
        // We can also directly provide the api (as its an interface implementation directly
        // from other dependencies:
        return retrofit.create(TwitterRestApi.class);
    }
}
