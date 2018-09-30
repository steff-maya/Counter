package com.jappy.counters.data.repository;

import com.jappy.counters.data.remote.CornershopApi;
import com.jappy.counters.data.repository.mapper.CountersToDomainMapper;
import com.jappy.counters.domain.Counter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CounterRepositoryImp implements CounterRepository {

    CornershopApi apiService;
    CountersToDomainMapper breedToDomainMapper;

    @Inject
    public CounterRepositoryImp(CornershopApi apiService, CountersToDomainMapper breedToDomainMapper) {
        this.apiService = apiService;
        this.breedToDomainMapper = breedToDomainMapper;
    }

    @Override
    public Single<List<Counter>> getCounters() {
        return apiService.getCounters().map(counterEntities -> breedToDomainMapper.map(counterEntities));
    }

    @Override
    public Single<List<Counter>> addCounter(Counter counter) {
        return apiService.addCounter(breedToDomainMapper.reverseMap(counter)).map(
                counterEntities -> breedToDomainMapper.map(counterEntities));
    }

    @Override
    public Single<List<Counter>> decreaseounter(Counter counter) {
        return apiService.decreaseounter(breedToDomainMapper.reverseMap(counter)).map(
                counterEntities -> breedToDomainMapper.map(counterEntities));
    }

    @Override
    public Single<List<Counter>> increaseCounter(Counter counter) {
        return apiService.increaseCounter(breedToDomainMapper.reverseMap(counter)).map(
                counterEntities -> breedToDomainMapper.map(counterEntities));
    }

    @Override
    public Single<List<Counter>> removeCounter(Counter counter) {
        return apiService.removeCounter(breedToDomainMapper.reverseMap(counter)).map(
                counterEntities -> breedToDomainMapper.map(counterEntities));
    }
}
