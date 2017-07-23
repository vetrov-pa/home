package ru.sbt.spring_study;

import ru.sbt.spring_study.parser.TradeFileCacheParser;

public class TradeFileParserDemo {
    public static void main(String[] args) {
        TradeFileCacheParser parser = new TradeFileCacheParser(new TradeFileParserConfig("parser-config.xml"));
        parser.parseFile("C:\\temp\\test.csv");
        parser.parseFile("C:\\temp\\test.csv");
        parser.parseFile("C:\\temp\\test.xls");
    }
}
