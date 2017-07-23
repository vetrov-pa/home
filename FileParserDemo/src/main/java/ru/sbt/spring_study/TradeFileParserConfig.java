package ru.sbt.spring_study;

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

    public TradeFileParserConfig(){
        // неудачное место для хранения таких связей.
        // наверное, это нужно делать как-то по-другому средствами Spring
        // или хотя бы хранить где-то в config файле
        // в любом случае мне не очень нравится, что я явно прописываю расширение на класс,
        // может это как-то можно сделать через аннотации
        configFileExt.put("csv", CSVTradeParser.class);
        configFileExt.put("xls", XLSTradeParser.class);
    }
    
    public Class<? extends TradeParser> getParser(String fileName){
        String fileExt = null;
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

        return configFileExt.get(fileExt);
    }
}
