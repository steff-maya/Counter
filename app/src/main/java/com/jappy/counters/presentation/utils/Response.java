package com.jappy.counters.presentation.utils;

import com.jappy.counters.domain.Counter;

import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.annotations.NonNull;

import static com.jappy.counters.presentation.utils.Status.ERROR;
import static com.jappy.counters.presentation.utils.Status.LOADING;
import static com.jappy.counters.presentation.utils.Status.SUCCESS;

public class Response {
    public  Status status;

    @Nullable
    public  List<Counter> data ;

    @Nullable
    public  Throwable error;

    @Nullable
    public  Message message;

    private Response(Status status, @Nullable List<Counter> data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;

    }

    private Response(@NonNull Message message) {
        this.message =message;
    }

    public static Response loading() {
        return new Response(LOADING, null, null);
    }

    public static Response success(@NonNull List<Counter> data) {
        return new Response(SUCCESS, data,null);
    }

    public static Response error(@NonNull Throwable error) {
        return new Response(ERROR, null, error);
    }

    public static Response message(@NonNull Message message) {
        return new Response(message);
    }

}
