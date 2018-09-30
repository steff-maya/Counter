package com.jappy.counters;

import com.google.common.collect.Lists;

import com.jappy.counters.data.entity.CounterEntity;
import com.jappy.counters.data.remote.CornershopApi;
import com.jappy.counters.data.repository.CounterRepositoryImp;
import com.jappy.counters.data.repository.mapper.CountersToDomainMapper;
import com.jappy.counters.domain.Counter;
import com.jappy.counters.util.CounterFixture;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import io.reactivex.Single;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DefaultCounterContentServiceTest {
    @InjectMocks
    public CountersToDomainMapper countersToDomainMapper;
    @Mock
    private CornershopApi mockApi;
    ;
    @InjectMocks
    private CounterRepositoryImp sut;
    private ArrayList<CounterEntity> showFixture;
    private Counter counter;
    private CounterEntity showFixtureItem;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        counter = new CounterFixture()
                .withTitle("show")
                .withCount(1)
                .withid("sdfsdf").buildItem();

        showFixtureItem = new CounterFixture()
                .withTitle("show")
                .withCount(1)
                .withid("sdfsdf").build();

        showFixture = Lists.newArrayList(new CounterFixture()
                        .withTitle("show")
                        .withCount(1)
                        .withid("sdfsdf").build(),
                new CounterFixture()
                        .withTitle("show")
                        .withCount(1)
                        .withid("67878d").build(),
                new CounterFixture()
                        .withTitle("show")
                        .withCount(1)
                        .withid("34fgd").build());

        when(mockApi.getCounters()).thenReturn(Single.just(Lists.newArrayList(showFixture)));

        when(mockApi.addCounter(showFixtureItem)).thenReturn(Single.just(Lists.newArrayList(showFixture)));

        when(mockApi.removeCounter(showFixtureItem)).thenReturn(Single.just(Lists.newArrayList(showFixture)));

        when(mockApi.decreaseounter(showFixtureItem)).thenReturn(Single.just(Lists.newArrayList(showFixture)));

        when(mockApi.increaseCounter(showFixtureItem)).thenReturn(Single.just(Lists.newArrayList(showFixture)));

        // Subject
        sut = new CounterRepositoryImp(mockApi, countersToDomainMapper);
    }

    @Test
    public void fetchCounter_shouldCallApiClient_getCounter() {
        sut.getCounters().blockingGet();
        verify(mockApi).getCounters();
    }

    @Test
    public void fetchCounter_shouldCallApiClient_addCounter() {
        //TODO:30/09/18 check why is failing  in the mapper
        sut.addCounter(counter).blockingGet();
        verify(mockApi).addCounter(showFixtureItem);
    }

    @Test
    public void fetchCounter_shouldCallApiClient_RemoveCounter() {
        //TODO:30/09/18 check why is failing  in the mapper
        sut.removeCounter(counter).blockingGet();
        verify(mockApi).removeCounter(showFixtureItem);
    }
}
