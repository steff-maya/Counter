package com.jappy.counters.data.repository;

import com.jappy.counters.domain.Counter;

import java.util.List;

import io.reactivex.Single;

public interface CounterRepository {

    Single<List<Counter>> getCounters();

    Single<List<Counter>> addCounter(Counter counter);

    Single<List<Counter>> decreaseCounter(Counter counter);

    Single<List<Counter>> increaseCounter(Counter counter);

    Single<List<Counter>> removeCounter(Counter counter);
}
