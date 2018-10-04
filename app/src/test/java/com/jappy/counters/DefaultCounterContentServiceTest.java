package com.jappy.counters;

import com.google.common.collect.Lists;

import com.jappy.counters.data.remote.CornershopApi;
import com.jappy.counters.data.repository.CounterRepositoryImp;
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


    @Mock
    private CornershopApi mockApi;
    ;
    @InjectMocks
    private CounterRepositoryImp sut;
    private ArrayList<Counter> counterFixture;
    private Counter counterFixtureItem;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

       

        counterFixtureItem = new CounterFixture()
                .withTitle("pollo")
                .withCount(1)
                .withid("qwee3").buildItem();

        counterFixture = Lists.newArrayList(new CounterFixture()
                        .withTitle("carne")
                        .withCount(1)
                        .withid("rter3").buildItem(),
                new CounterFixture()
                        .withTitle("queso")
                        .withCount(1)
                        .withid("sdft8").buildItem(),
                new CounterFixture()
                        .withTitle("Arroz")
                        .withCount(1)
                        .withid("sdfsd0").buildItem());

        when(mockApi.getCounters()).thenReturn(Single.just(Lists.newArrayList(counterFixture)));

        when(mockApi.addCounter(counterFixtureItem)).thenReturn(Single.just(Lists.newArrayList(counterFixture)));

        when(mockApi.removeCounter(counterFixtureItem)).thenReturn(Single.just(Lists.newArrayList(counterFixture)));

        when(mockApi.decreaseCounter(counterFixtureItem)).thenReturn(Single.just(Lists.newArrayList(counterFixture)));

        when(mockApi.increaseCounter(counterFixtureItem)).thenReturn(Single.just(Lists.newArrayList(counterFixture)));

        // Subject
        sut = new CounterRepositoryImp(mockApi);
    }

    @Test
    public void fetchCounter_shouldCallApiClient_getCounter() {
        sut.getCounters().blockingGet();
        verify(mockApi).getCounters();
    }

    @Test
    public void fetchCounter_shouldCallApiClient_addCounter() {
        sut.addCounter(counterFixtureItem).blockingGet();
        verify(mockApi).addCounter(counterFixtureItem);

    }

    @Test
    public void fetchCounter_shouldCallApiClient_increaseCounter() {

        sut.increaseCounter(counterFixtureItem).blockingGet();
        verify(mockApi).increaseCounter(counterFixtureItem);
    }

    @Test
    public void fetchCounter_shouldCallApiClient_decreaseCounter() {

        sut.decreaseCounter(counterFixtureItem).blockingGet();
        verify(mockApi).decreaseCounter(counterFixtureItem);
    }

    @Test
    public void fetchCounter_shouldCallApiClient_RemoveCounter() {

        sut.removeCounter(counterFixtureItem).blockingGet();
        verify(mockApi).removeCounter(counterFixtureItem);
    }
}
