package com.jappy.counters.data.repository.mapper;

import com.jappy.counters.data.entity.CounterEntity;
import com.jappy.counters.domain.Counter;

import javax.inject.Inject;

public class CountersToDomainMapper extends Mapper<CounterEntity, Counter> {

    @Inject
    public CountersToDomainMapper() {
    }

    @Override
    public Counter map(final CounterEntity value) {
        Counter counter = new Counter();
        counter.count = value.count;
        counter.id = value.id;
        counter.title = value.title;

        return counter;
    }

    @Override
    public CounterEntity reverseMap(final Counter value) {
        CounterEntity entity = new CounterEntity();
        entity.count = value.count;
        entity.id = value.id;
        entity.title = value.title;

        return entity;
    }
}
