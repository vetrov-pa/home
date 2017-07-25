package ru.sbt.spring_study.parser;

import ru.sbt.spring_study.Trade;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

@ParserForFile(fileExt = "xls")
public class XLSTradeParser implements TradeParser {
    public Collection<Trade> parse(InputStream inputStream) {
        System.out.println("XLS parser working");
        return new ArrayList<Trade>();
    }
}
