package ru.sbt.spring_study.parser;

import org.springframework.stereotype.Component;
import ru.sbt.spring_study.Trade;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

@Component
@ParserForFile(fileExt = "csv")
public class CSVTradeParser implements TradeParser {
    public Collection<Trade> parse(InputStream inputStream) {
        Collection<Trade> trades = new ArrayList<Trade>();
        System.out.println("CSV parser working");
        return trades;
    }
}
