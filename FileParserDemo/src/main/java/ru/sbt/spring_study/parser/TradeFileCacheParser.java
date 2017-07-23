package ru.sbt.spring_study.parser;

import ru.sbt.spring_study.Trade;
import ru.sbt.spring_study.TradeFileParserConfig;
import ru.sbt.spring_study.cache.TradeFileCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

public class TradeFileCacheParser {
    private TradeFileCache tradeFileCache;
    private TradeFileParserConfig parserConfig;

    public TradeFileCacheParser(TradeFileParserConfig parserConfig) {
        this.parserConfig = parserConfig;
        tradeFileCache = parserConfig.getTradeFileCache();
    }

    public Collection<Trade> parseFile(String filePath){
        System.out.println("Parsing file: " + filePath);
        File file = new File(filePath);
        // проверяем на наличие в кэше
        Collection<Trade> trades = tradeFileCache.find(file);

        // если не нашли, то парсим и кладем в кэш
        if (trades == null) {
            // получили парсер, используя расширение файла
            TradeParser tradeParser = parserConfig.getTradeParser(filePath);

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
