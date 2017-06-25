package com.java.urlshortener.dao;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.urlshortener.model.URLData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by ghegde on 6/25/17.
 */
@Component(value="redisDataStore")
public class RedisDataStoreDao implements DataStoreDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryDataStoreDao.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void store(URLData data) throws IOException {
        LOGGER.info("storing data in redis for key {}", data.getKey());
        ObjectMapper objectMapper = new ObjectMapper();
        redisTemplate.opsForValue().set(data.getKey(), objectMapper.writeValueAsString(data));
    }

    @Override
    public URLData retrieve(String key) throws IOException {
        LOGGER.info("Retrieving data from redis for key {}", key);
        ObjectMapper objectMapper = new ObjectMapper();
        Object dataString = redisTemplate.opsForValue().get(key);
        if(dataString == null){
            return null;
        }
        URLData data = objectMapper.readValue(dataString.toString(), URLData.class);
        return data;
    }
}
