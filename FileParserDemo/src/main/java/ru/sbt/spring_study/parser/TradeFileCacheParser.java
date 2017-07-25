package ru.sbt.spring_study.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.sbt.spring_study.Trade;
import ru.sbt.spring_study.cache.TradeFileCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

@Component
public class TradeFileCacheParser {
    @Autowired
    private TradeFileCache tradeFileCache;
    @Autowired
    private TradeParserContainer tradeParserContainer;

    public TradeFileCacheParser() {
    }

    public Collection<Trade> parseFile(String filePath) throws Exception{
        System.out.println("Parsing file: " + filePath);
        File file = new File(filePath);
        // проверяем на наличие в кэше
        if (tradeFileCache == null){
            throw new RuntimeException("Не нашли реализацию кэша");
        }
        Collection<Trade> trades = tradeFileCache.find(file);

        // если не нашли, то парсим и кладем в кэш
        if (trades == null) {
            // получили парсер, используя расширение файла
            TradeParser tradeParser = tradeParserContainer.getTradeParser(filePath);
            if (tradeParser == null){
                throw new RuntimeException("Не нашли реализацию парсера для " + filePath);
            }

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
