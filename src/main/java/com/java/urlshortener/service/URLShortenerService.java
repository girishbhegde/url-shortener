package com.java.urlshortener.service;

import com.java.urlshortener.dao.DataStoreDao;
import com.java.urlshortener.model.URLData;
import com.java.urlshortener.tools.Base62Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by ghegde on 6/25/17.
 */
@Service
public class URLShortenerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLShortenerService.class);
    private int SHORT_URL_SIZE = 7;
    private Base62Converter base62Converter = new Base62Converter();
    private DataStoreDao dataStoreDao;

    @Autowired
    private Environment env;

    @Autowired
    @Qualifier("memoryDataStore")
    private DataStoreDao memoryDataStore;

    @Autowired
    @Qualifier("redisDataStore")
    private DataStoreDao redisDataStore;

    //TODO replace with factory
    /**
     * This reads from config whch datastore to use and sets that dao.
     */
    @PostConstruct
    public void init(){
        if("redis".equals(env.getProperty("datastore.type"))){
            dataStoreDao = redisDataStore;
        }else{
            dataStoreDao = memoryDataStore;
        }
    }


    public String shorten(String longUrl){
        LOGGER.debug("Request for shortening. URL-> {}", longUrl);

        String uniqueKey = createUniqueKey();
        URLData data = new URLData(uniqueKey, longUrl);

        try {
            dataStoreDao.store(data);
        } catch (IOException e) {
            LOGGER.error("Exception occurred while storing data for url {}", longUrl, e);
            return null;
        }

        LOGGER.debug("Shortened URL key-> {} for URL {}",longUrl, uniqueKey);
        return constructURL(uniqueKey);
    }

    private String constructURL(String uniqueKey) {
        return "http://localhost:" + env.getProperty("server.port") + "/" + uniqueKey;
    }

    public URLData getLongUrl(String shortUrl){

        try {
            return dataStoreDao.retrieve(shortUrl);
        } catch (IOException e) {
            LOGGER.error("Exception occurred while retrieving data for key {}", shortUrl, e);
            return null;
        }
    }

    private String createUniqueKey() {
        StringBuilder shortUrlBuilder = new StringBuilder();
        for(int i=0;i< SHORT_URL_SIZE;i++){
            shortUrlBuilder.append(base62Converter.getRandomChar());
        }
        return shortUrlBuilder.toString();
    }
}
