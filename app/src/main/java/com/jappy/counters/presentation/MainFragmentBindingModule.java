package com.jappy.counters.presentation;

import com.jappy.counters.presentation.counter.CounterFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract CounterFragment provideCounterFragment();
}
