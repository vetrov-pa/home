package ru.sbt.spring_study.cache;

import ru.sbt.spring_study.Trade;

import java.io.File;
import java.util.Collection;

public interface TradeFileCache {
    Collection<Trade> find(File file);
    void put(File file, Collection<Trade> trades);
}
