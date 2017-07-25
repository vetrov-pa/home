package ru.sbt.spring_study;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.sbt.spring_study.cache.TradeFileCache;
import ru.sbt.spring_study.cache.TradeFileCacheMap;
import ru.sbt.spring_study.parser.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "ru.sbt")
public class TradeFileParserConfig {
    @Bean
    public TradeFileCache getTradeFileCache() {
        return new TradeFileCacheMap();
    }

    @Bean
    public TradeParserContainer getParserContainer(){
        Map<String, Class<?>> parsers = new HashMap<String, Class<?>>();
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            Class<?> retType = method.getReturnType();
            ParserForFile parserForFile = retType.getAnnotation(ParserForFile.class);
            if (parserForFile != null){
                parsers.put(parserForFile.fileExt(), retType);
            }
        }
        return new TradeParserContainer(parsers);
    }

    public CSVTradeParser getCSVTradeParser(){
        return new CSVTradeParser();
    }

    public XLSTradeParser getXLSTradeParser(){
        return new XLSTradeParser();
    }
}
