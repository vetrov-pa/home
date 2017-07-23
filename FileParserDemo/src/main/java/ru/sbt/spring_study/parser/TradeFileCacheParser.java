package ru.sbt.spring_study.parser;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sbt.spring_study.Trade;
import ru.sbt.spring_study.TradeFileParserConfig;
import ru.sbt.spring_study.cache.TradeFileCache;
import ru.sbt.spring_study.cache.TradeFileCacheMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

public class TradeFileCacheParser {
    private TradeFileCache tradeFileCache;
    private ApplicationContext context = new ClassPathXmlApplicationContext("parser-config.xml");
    private TradeFileParserConfig parserConfig = new TradeFileParserConfig();

    public TradeFileCacheParser() {
        tradeFileCache = (TradeFileCache)context.getBean(TradeFileCache.class);
    }

    public Collection<Trade> parseFile(String filePath){
        System.out.println("Parsing file: " + filePath);
        File file = new File(filePath);
        // проверяем на наличие в кэше
        Collection<Trade> trades = tradeFileCache.find(file);

        if (trades == null) {
            Class<? extends TradeParser> parserClass = parserConfig.getParser(filePath);
            TradeParser tradeParser = context.getBean(parserClass);

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
