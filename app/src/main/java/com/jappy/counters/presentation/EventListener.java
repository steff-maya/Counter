package com.jappy.counters.presentation;

import com.jappy.counters.domain.Counter;

public interface EventListener {
    void increaseCounter(Counter item);
    void decreaseCounter(Counter item);
    void removeCounter(Counter item);
}