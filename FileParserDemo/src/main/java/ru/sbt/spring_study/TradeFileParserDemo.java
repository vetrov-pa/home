package ru.sbt.spring_study;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sbt.spring_study.parser.TradeFileCacheParser;

public class TradeFileParserDemo {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(TradeFileParserConfig.class);
        TradeFileCacheParser parser = context.getBean(TradeFileCacheParser.class);
        parser.parseFile("C:\\temp\\test.csv");
        parser.parseFile("C:\\temp\\test.csv");
        parser.parseFile("C:\\temp\\test.xls");
    }
}
