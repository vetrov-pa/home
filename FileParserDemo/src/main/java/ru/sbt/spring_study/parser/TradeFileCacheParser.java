package ru.sbt.spring_study.parser;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sbt.spring_study.Trade;
import ru.sbt.spring_study.cache.TradeFileCache;
import ru.sbt.spring_study.cache.TradeFileCacheMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

public class TradeFileCacheParser {
    private TradeFileCache tradeFileCache;
    private ApplicationContext context;

    public TradeFileCacheParser() {
        context = new ClassPathXmlApplicationContext("parser-config.xml");
        tradeFileCache = (TradeFileCache)context.getBean("TradeFileCache");
    }

    public Collection<Trade> parseFile(String filePath){
        System.out.println("Parsing file: " + filePath);
        File file = new File(filePath);
        // проверяем на наличие в кэше
        Collection<Trade> trades = tradeFileCache.find(file);

        if (trades == null) {
            TradeParser tradeParser = context.getBean(CSVTradeParser.class);

            try {
                trades = tradeParser.parse(new FileInputStream(filePath));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(filePath + " not found.");
            }

            tradeFileCache.put(file, trades);
        }

        return trades;
    }
}
