package com.jappy.counters.data.remote;

import com.jappy.counters.domain.Counter;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface CornershopApi {
    //GET /api/v1/counters
    //[]
    @GET ("/api/v1/counters")
    Single<List<Counter>> getCounters();

    //POST {title: "bob"} /api/v1/counter
    //[{id: "asdf", title: "bob", count: 0}]
    @POST ("/api/v1/counter")
    Single<List<Counter>> addCounter(@Body Counter counter);

    //POST {id: "asdf"} /api/v1/counter/inc
    //[{id: "asdf", title: "bob", count: 1}]
    @POST ("/api/v1/counter/inc")
    Single<List<Counter>> increaseCounter(@Body Counter counter);

    //POST {id: "qwer"} /api/v1/counter/dec
    //[{id: "qwer", title: "bob", count: 1}]
    @POST ("/api/v1/counter/dec")
    Single<List<Counter>> decreaseCounter(@Body Counter counter);

    //DELETE {id: "qwer"} /api/v1/counter
    //[]
    @HTTP (method = "DELETE", path = "/api/v1/counter", hasBody = true)
    Single<List<Counter>> removeCounter(@Body Counter counter);
}
