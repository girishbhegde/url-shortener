package com.java.urlshortener.dao;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.java.urlshortener.model.URLData;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by ghegde on 6/25/17.
 */
@Service
public interface DataStoreDao {
    void store(URLData data) throws IOException;
    URLData retrieve(String key) throws IOException;
}
