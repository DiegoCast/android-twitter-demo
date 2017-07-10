package com.diegocast.twitterapp.data.persistance;

import com.diegocast.twitterapp.domain.model.Response;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import rx.Observable;

/**
 * Repository that deals with data persistence
 */

public class PersistanceRepository {

    public Observable<Response<Void, Boolean>> save(Tweet tweet) {
        //TODO save user favorite tweet through realm instance
        return null;
    }

    public Observable<Response<List<Tweet>, Boolean>> favorites() {
        //TODO provide user favorite tweets through realm instance
        return null;
    }
}
