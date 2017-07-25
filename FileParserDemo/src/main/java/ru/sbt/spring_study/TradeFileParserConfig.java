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
    public TradeParserContainer getParserContainer(){
        Map<String, TradeParser> tradeParserBeans = context.getBeansOfType(TradeParser.class);
        Map<String, Class<? extends TradeParser>> parsers = new HashMap<String, Class<? extends TradeParser>>();
        for (TradeParser tradeParser : tradeParserBeans.values()) {
            ParserForFile parserForFile = tradeParser.getClass().getAnnotation(ParserForFile.class);
            if (parserForFile != null){
                parsers.put(parserForFile.fileExt(), tradeParser.getClass());
            }
        }
        return new TradeParserContainer(parsers);
    }
}
