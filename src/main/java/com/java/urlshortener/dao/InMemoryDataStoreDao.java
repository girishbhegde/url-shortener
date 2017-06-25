package com.java.urlshortener.dao;

import com.java.urlshortener.model.URLData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ghegde on 6/25/17.
 */
@Component(value="memoryDataStore")
public class InMemoryDataStoreDao implements DataStoreDao{
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryDataStoreDao.class);
    private Map<String, URLData> map = new ConcurrentHashMap<>();

    @Override
    public void store(URLData data) {
        LOGGER.info("storing data in memory for key {}", data.getKey());
        map.put(data.getKey(), data);
    }

    @Override
    public URLData retrieve(String key) {
        LOGGER.info("Retrieving data from memory for key {}", key);
        return map.get(key);
    }
}
