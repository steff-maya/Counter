package com.jappy.counters.presentation.counter;

import com.jappy.counters.data.repository.CounterRepositoryImp;
import com.jappy.counters.domain.Counter;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CounterViewModel extends ViewModel {

    //TODO:30/09/18  wrap all LiveData in one CustomEvent

    private final MutableLiveData<List<Counter>> counter = new MutableLiveData<>();
    private final MutableLiveData<Boolean> counterError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> counterOperationError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> counterloading = new MutableLiveData<>();
    private final MutableLiveData<Integer> counterTotal = new MutableLiveData<>();
    private CounterRepositoryImp repoRepository;
    private CompositeDisposable disposable;

    @Inject
    public CounterViewModel(CounterRepositoryImp counterRepositoryImp) {
        this.repoRepository = counterRepositoryImp;
        disposable = new CompositeDisposable();
        getCounter();
    }

    LiveData<List<Counter>> getCounters() {
        return counter;
    }
    LiveData<Boolean> getcounterOperationError() {
        return counterOperationError;
    }

    LiveData<Boolean> getError() {
        return counterError;
    }

    LiveData<Boolean> getLoading() {
        return counterloading;
    }

    LiveData<Integer> getTotal() {
        return counterTotal;
    }

    public void removeCounter(final Counter item) {
        disposable.add(repoRepository.removeCounter(item).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Counter>>() {

                    @Override
                    public void onSuccess(final List<Counter> counters) {
                        int total = 0;
                        for (Counter c : counters) {
                            total += c.count;
                        }
                        Collections.reverse(counters);
                        counterTotal.setValue(total);
                        counter.setValue(counters);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        counterloading.setValue(false);
                        counterOperationError.setValue(true);
                    }
                }));
    }

    public void decreaseounter(final Counter item) {
        disposable.add(repoRepository.decreaseounter(item).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Counter>>() {

                    @Override
                    public void onSuccess(final List<Counter> counters) {
                        int total = 0;
                        for (Counter c : counters) {
                            total += c.count;
                        }
                        Collections.reverse(counters);
                        counterTotal.setValue(total);
                        counter.setValue(counters);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        counterloading.setValue(false);
                        counterOperationError.setValue(true);
                    }
                }));
    }

    public void increaseCounter(final Counter item) {

        disposable.add(repoRepository.increaseCounter(item).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Counter>>() {

                    @Override
                    public void onSuccess(final List<Counter> counters) {
                        int total = 0;
                        for (Counter c : counters) {
                            total += c.count;
                        }
                        Collections.reverse(counters);
                        counterTotal.setValue(total);
                        counter.setValue(counters);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        counterloading.setValue(false);
                        counterOperationError.setValue(true);
                    }
                }));
    }

    public void addCounter(final String s) {
        counterloading.setValue(true);
        Counter itemCounter = new Counter();
        itemCounter.title = s;
        disposable.add(repoRepository.addCounter(itemCounter).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Counter>>() {

                    @Override
                    public void onSuccess(final List<Counter> counters) {
                        int total = 0;
                        for (Counter c : counters) {
                            total += c.count;
                        }
                        Collections.reverse(counters);
                        counterTotal.setValue(total);
                        counter.setValue(counters);
                        counterloading.setValue(false);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        counterloading.setValue(false);
                        counterOperationError.setValue(true);
                    }
                }));
    }

    private void getCounter() {
        counterloading.setValue(true);
        disposable.add(repoRepository.getCounters().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Counter>>() {

                    @Override
                    public void onSuccess(final List<Counter> counters) {
                        int total = 0;
                        for (Counter c : counters) {
                            total += c.count;
                        }
                        Collections.reverse(counters);
                        counterTotal.setValue(total);
                        counterError.setValue(false);
                        counter.setValue(counters);
                        counterloading.setValue(false);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        counterError.setValue(true);
                        counterloading.setValue(false);
                    }
                })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
