package ru.sbt.spring_study.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.sbt.spring_study.Trade;
import ru.sbt.spring_study.cache.TradeFileCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;

@Component
public class TradeFileCacheParser {
    @Autowired
    private TradeFileCache tradeFileCache;
    @Autowired
    private TradeFileParserContainer parserContainer;

    public TradeFileCacheParser() {
    }

    private String getFileExt(String filePath){
        String fileExt = null;
        if (filePath.lastIndexOf(".") != -1 && filePath.lastIndexOf(".") != 0)
            fileExt = filePath.substring(filePath.lastIndexOf(".") + 1);

        return fileExt;
    }

    public Collection<Trade> parseFile(String filePath) throws Exception{
        System.out.println("Parsing file: " + filePath);
        File file = new File(filePath);

        if (parserContainer.size() == 0){
            throw new RuntimeException("Не зарегистрировано ни одного парсера");
        }

        // проверяем на наличие в кэше
        if (tradeFileCache == null){
            throw new RuntimeException("Не нашли реализацию кэша");
        }
        Collection<Trade> trades = tradeFileCache.find(file);

        // если не нашли, то парсим и кладем в кэш
        if (trades == null) {
            // получили парсер, используя расширение файла
            String fileExt = getFileExt(filePath);
            TradeParser tradeParser = parserContainer.get(fileExt);
            if (tradeParser == null){
                throw new RuntimeException("Не нашли реализацию парсера для " + fileExt);
            }

            try {
                trades = tradeParser.parse(new FileInputStream(filePath));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(filePath + " не найден");
            }

            tradeFileCache.put(file, trades);
        }

        return trades;
    }
}
