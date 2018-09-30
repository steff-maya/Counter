package com.jappy.counters.data.remote;

import com.jappy.counters.data.entity.CounterEntity;

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
    Single<List<CounterEntity>> getCounters();

    //POST {title: "bob"} /api/v1/counter
    //[{id: "asdf", title: "bob", count: 0}]
    @POST ("/api/v1/counter")
    Single<List<CounterEntity>> addCounter(@Body CounterEntity counter);

    //POST {id: "asdf"} /api/v1/counter/inc
    //[{id: "asdf", title: "bob", count: 1}]
    @POST ("/api/v1/counter/inc")
    Single<List<CounterEntity>> increaseCounter(@Body CounterEntity counter);

    //POST {id: "qwer"} /api/v1/counter/dec
    //[{id: "qwer", title: "bob", count: 1}]
    @POST ("/api/v1/counter/dec")
    Single<List<CounterEntity>> decreaseounter(@Body CounterEntity counter);

    //DELETE {id: "qwer"} /api/v1/counter
    //[]
    @HTTP (method = "DELETE", path = "/api/v1/counter", hasBody = true)
    Single<List<CounterEntity>> removeCounter(@Body CounterEntity counter);
}
