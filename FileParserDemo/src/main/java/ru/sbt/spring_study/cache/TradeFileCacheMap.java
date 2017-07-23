package ru.sbt.spring_study.cache;

import ru.sbt.spring_study.Trade;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

public class TradeFileCacheMap implements TradeFileCache {
    private HashMap<String, Collection<Trade>> cache = new HashMap<String, Collection<Trade>>();

    public TradeFileCacheMap() {
        System.out.println("Cache created");
    }

    private String getFileCacheKey(File file){
        String key = null;

        if (file != null) {
            key = file.getAbsolutePath() + file.lastModified() + file.length();
        }

        return key;
    }
    public Collection<Trade> find(File file) {
        String key = getFileCacheKey(file);
        Collection<Trade> trades = cache.get(key);


        String foundMessage = trades == null ? " not found " : " found ";
        System.out.println("File " + file.getName() + foundMessage + "in cache");

        return trades;
    }

    public void put(File file, Collection<Trade> trades) {
        String key = getFileCacheKey(file);
        cache.put(key, trades);
        System.out.println("File " + file.getName() + " put in cache, key = " + key);
    }
}
