package com.jappy.counters.presentation.counter;

import com.jappy.counters.data.repository.CounterRepositoryImp;
import com.jappy.counters.domain.Counter;
import com.jappy.counters.presentation.utils.Message;
import com.jappy.counters.presentation.utils.Response;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CounterViewModel extends ViewModel {

    //TODO:30/09/18  wrap all LiveData in one CustomEvent

    private final MutableLiveData<Response> counter = new MutableLiveData<>();
    private final MutableLiveData<Response> message = new MutableLiveData<>();
    private final MutableLiveData<String> total = new MutableLiveData<>();
    List<Counter> countersList;
    private CounterRepositoryImp repoRepository;
    private CompositeDisposable disposable;

    @Inject
    public CounterViewModel(CounterRepositoryImp counterRepositoryImp) {
        this.repoRepository = counterRepositoryImp;
        disposable = new CompositeDisposable();
        countersList = new ArrayList<>();
        getCounter();
    }

    MutableLiveData<Response> getResponse() {
        return counter;
    }

    MutableLiveData<Response> getMessage() {
        return message;
    }

    MutableLiveData<String> getTotal() {
        return total;
    }



    public void removeCounter(final Counter item) {
        disposable.add(repoRepository.removeCounter(item).subscribeOn(Schedulers.io())
                .doOnSubscribe(__ -> counter.setValue(Response.loading()))
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Counter>>() {

                    @Override
                    public void onSuccess(final List<Counter> counters) {

                        countersList.addAll(counters);
                        counter.setValue(Response.success(counters));
                        total.setValue(getTotal(counters));
                        message.setValue(Response.message(Message.REMOVE));
                        message.setValue(null);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        counter.setValue(Response.error(e));
                    }
                }));
    }

    public void decreaseounter(final Counter item) {
        disposable.add(repoRepository.decreaseCounter(item).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Counter>>() {

                    @Override
                    public void onSuccess(final List<Counter> counters) {
                        countersList.addAll(counters);
                        counter.setValue(Response.success(counters));
                        total.setValue(getTotal(counters));
                    }

                    @Override
                    public void onError(final Throwable e) {
                        counter.setValue(Response.error(e));
                    }
                }));
    }

    public void increaseCounter(final Counter item) {

        disposable.add(repoRepository.increaseCounter(item).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Counter>>() {

                    @Override
                    public void onSuccess(final List<Counter> counters) {
                        total.setValue(getTotal(counters));
                        counter.setValue(Response.success(counters));
                        countersList.addAll(counters);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        counter.setValue(Response.error(e));
                    }
                }));
    }

    public void deleteAllCounter() {
        if (countersList.size() > 0) {
            counter.setValue(Response.loading());
            for (Counter item : countersList) {
                disposable.add(repoRepository.removeCounter(item).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Counter>>() {

                            @Override
                            public void onSuccess(final List<Counter> counters) {
                                if (counters.size() == 0) {
                                    countersList.addAll(counters);
                                    total.setValue(getTotal(counters));
                                    counter.setValue(Response.success(counters));
                                    message.setValue(Response.message(Message.REMOVE));
                                    message.setValue(null);
                                }
                            }

                            @Override
                            public void onError(final Throwable e) {
                                counter.setValue(Response.error(e));
                            }
                        }));
            }
        }else {
            message.setValue(Response.message(Message.NOACTION));
            message.setValue(null);
        }
    }

    public void addCounter(final String s) {

        if (s != null && s.length() > 0) {
            Counter itemCounter = new Counter();
            itemCounter.title = s;
            disposable.add(repoRepository.addCounter(itemCounter).subscribeOn(Schedulers.io())
                    .doOnSubscribe(__ -> counter.setValue(Response.loading()))
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Counter>>() {

                        @Override
                        public void onSuccess(final List<Counter> counters) {
                            counter.setValue(Response.success(counters));
                            total.setValue(getTotal(counters));
                            countersList.addAll(counters);
                            message.setValue(Response.message(Message.ADD));
                            message.setValue(null);
                        }

                        @Override
                        public void onError(final Throwable e) {
                            counter.setValue(Response.error(e));
                        }
                    }));
        }
    }

    private void getCounter() {

        disposable.add(repoRepository.getCounters().subscribeOn(Schedulers.io())
                .doOnSubscribe(__ -> counter.setValue(Response.loading()))
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Counter>>() {

                    @Override
                    public void onSuccess(final List<Counter> counters) {
                        counter.setValue(Response.success(counters));
                        countersList.addAll(counters);
                        total.setValue(getTotal(counters));
                    }

                    @Override
                    public void onError(final Throwable e) {
                        counter.setValue(Response.error(e));
                    }
                })
        );
    }

    private String getTotal(List<Counter> counters) {
        int total = 0;
        for (Counter c : counters) {
            total += c.count;
        }
        return String.valueOf(total);
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
