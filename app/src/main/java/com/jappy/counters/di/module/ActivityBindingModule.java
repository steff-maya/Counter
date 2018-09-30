package com.jappy.counters.di.module;

import com.jappy.counters.presentation.MainActivity;
import com.jappy.counters.presentation.MainFragmentBindingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector (modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();
}
