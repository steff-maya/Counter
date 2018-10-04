package com.jappy.counters.data.repository;

import com.jappy.counters.data.remote.CornershopApi;
import com.jappy.counters.domain.Counter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CounterRepositoryImp implements CounterRepository {

    CornershopApi apiService;

    @Inject
    public CounterRepositoryImp(CornershopApi apiService) {
        this.apiService = apiService;

    }

    @Override
    public Single<List<Counter>> getCounters() {
        return apiService.getCounters();
    }

    @Override
    public Single<List<Counter>> addCounter(Counter counter) {
        return apiService.addCounter(counter);
    }


    @Override
    public Single<List<Counter>> decreaseCounter(Counter counter) {
        return apiService.decreaseCounter(counter);
    }

    @Override
    public Single<List<Counter>> increaseCounter(Counter counter) {
        return apiService.increaseCounter(counter);
    }

    @Override
    public Single<List<Counter>> removeCounter(Counter counter) {
        return apiService.removeCounter(counter);
    }
}
