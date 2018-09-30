package com.jappy.counters.util;

import com.jappy.counters.data.entity.CounterEntity;
import com.jappy.counters.domain.Counter;

public class CounterFixture {

    public String id;
    public String title;
    public int count;

    public CounterFixture withTitle(String val) {
        title = val;
        return this;
    }

    public CounterFixture withid(String val) {
        id = val;
        return this;
    }

    public CounterFixture withCount(int val) {
        count = val;
        return this;
    }

    public CounterEntity build() {
        CounterEntity counterItem = new CounterEntity();
        counterItem.setTitle(title);
        counterItem.setCount(count);
        counterItem.setId(id);

        return counterItem;
    }

    public Counter buildItem() {
        Counter counterItem = new Counter();
        counterItem.setTitle(title);
        counterItem.setCount(count);
        counterItem.setId(id);

        return counterItem;
    }
}
