package com.jappy.counters.presentation;

import com.jappy.counters.domain.Counter;

public interface EventListener {
    void increaseCounter(Counter item);
    void decreaseounter(Counter item);
    void removeCounter(Counter item);
    void seletecCounter(Counter item);
}