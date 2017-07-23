package ru.sbt.spring_study;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sbt.spring_study.cache.TradeFileCache;
import ru.sbt.spring_study.parser.AssociatedFileExt;
import ru.sbt.spring_study.parser.CSVTradeParser;
import ru.sbt.spring_study.parser.TradeParser;
import ru.sbt.spring_study.parser.XLSTradeParser;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 1 on 23.07.2017.
 */
public class TradeFileParserConfig {
    // мапа для хранения связки расширение файла - класс реализации парсера
    private Map<String, Class<? extends TradeParser>> configFileExt = new HashMap<String, Class<? extends TradeParser>>();
    private ApplicationContext context = new ClassPathXmlApplicationContext("parser-config.xml");

    public ApplicationContext getContext() {
        return context;
    }

    public TradeFileParserConfig(){
        // неудачное место для хранения таких связей.
        // наверное, это нужно делать как-то по-другому средствами Spring
        // или хотя бы хранить где-то в config файле
        // в любом случае мне не очень нравится, что я явно прописываю расширение на класс,
        // может это как-то можно сделать через аннотации
        //configFileExt.put("csv", CSVTradeParser.class);
        //configFileExt.put("xls", XLSTradeParser.class);

        // вторая попытка - через аннотации к классам реализации парсера
        final Map<String, TradeParser> beansOfType = context.getBeansOfType(TradeParser.class);
        for (TradeParser tradeParser : beansOfType.values()) {
            AssociatedFileExt associatedFileExt = tradeParser.getClass().getAnnotation(AssociatedFileExt.class);
            if (associatedFileExt != null){
                System.out.println(tradeParser);
                configFileExt.put(associatedFileExt.fileExt(), tradeParser.getClass());
            }

        }
    }

    public TradeFileCache getTradeFileCache() {
        return context.getBean(TradeFileCache.class);
    }

    private Class<? extends TradeParser> getParserClass(String fileName){
        String fileExt = null;
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

        return configFileExt.get(fileExt);
    }

    public TradeParser getTradeParser(String filePath){
        Class<? extends TradeParser> parserClass = getParserClass(filePath);
        return context.getBean(parserClass);
    }
}
