package ru.sbt.spring_study.parser;

import ru.sbt.spring_study.Trade;

import java.io.InputStream;
import java.util.Collection;

public interface TradeParser {
    Collection<Trade> parse(InputStream inputStream);
}
