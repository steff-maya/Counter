package com.jappy.counters.di.module;

import com.jappy.counters.data.remote.ApiServiceFactory;
import com.jappy.counters.data.remote.CornershopApi;
import com.jappy.counters.data.repository.CounterRepository;
import com.jappy.counters.data.repository.CounterRepositoryImp;
import com.jappy.counters.presentation.counter.adapter.CounterListAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Singleton
@Module (includes = ViewModelModule.class)
public class ApplicationModule {

    @Provides
    CounterListAdapter providesAdaoter() {
        return new CounterListAdapter();
    }

    @Provides
    CounterRepository providesRepository(CornershopApi api) {
        return new CounterRepositoryImp(api);
    }

    @Provides
    CornershopApi providesApiService() {
        return ApiServiceFactory.build(new OkHttpClient(), CornershopApi.class);
    }
}
