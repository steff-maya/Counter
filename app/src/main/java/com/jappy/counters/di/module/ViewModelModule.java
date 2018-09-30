package com.jappy.counters.di.module;

import com.jappy.counters.base.ViewModelFactory;
import com.jappy.counters.presentation.counter.CounterViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Singleton
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey (CounterViewModel.class)
    abstract ViewModel bindCounterViewModel(CounterViewModel listViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
