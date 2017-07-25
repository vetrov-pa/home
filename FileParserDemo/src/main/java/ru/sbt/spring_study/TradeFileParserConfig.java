package ru.sbt.spring_study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.sbt.spring_study.cache.TradeFileCache;
import ru.sbt.spring_study.cache.TradeFileCacheMap;
import ru.sbt.spring_study.parser.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "ru.sbt")
public class TradeFileParserConfig {
    @Autowired
    ApplicationContext context;

    @Bean
    public TradeFileCache getTradeFileCache() {
        return new TradeFileCacheMap();
    }

    @Bean
    public TradeFileParserContainer getParsers(){
        Map<String, TradeParser> tradeParserBeans = context.getBeansOfType(TradeParser.class);
        TradeFileParserContainer parserContainer = new TradeFileParserContainer();
        for (TradeParser tradeParser : tradeParserBeans.values()) {
            ParserForFile parserForFile = tradeParser.getClass().getAnnotation(ParserForFile.class);
            if (parserForFile != null){
                parserContainer.put(parserForFile.fileExt(), tradeParser);
            }
        }
        return parserContainer;
    }
}
