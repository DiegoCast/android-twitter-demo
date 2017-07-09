package com.diegocast.twitterapp.domain.model;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

/**
 * Domain model for response data wrapping
 */

@AutoValue
public abstract class Response<T, S> {
    public static <T, S> Response<T, S> create(@Nullable T data, S state) {
        return new AutoValue_Response<>(data, state);
    }

    @Nullable
    public abstract T data();

    public abstract S state();
}
