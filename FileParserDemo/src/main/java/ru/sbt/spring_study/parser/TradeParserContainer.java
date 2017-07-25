package ru.sbt.spring_study.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 1 on 25.07.2017.
 */
public class TradeParserContainer {
    private final Map<String, Class<? extends TradeParser>> parsers;

    public TradeParserContainer(Map<String, Class<? extends TradeParser>> parsers){
        this.parsers = parsers;
    }

    public TradeParser getTradeParser(String filePath) throws Exception{
        String fileExt = null;
        if (filePath.lastIndexOf(".") != -1 && filePath.lastIndexOf(".") != 0)
            fileExt = filePath.substring(filePath.lastIndexOf(".") + 1);

        Class<? extends TradeParser> parserClass = parsers.get(fileExt);

        return parserClass.newInstance();
    }
}
