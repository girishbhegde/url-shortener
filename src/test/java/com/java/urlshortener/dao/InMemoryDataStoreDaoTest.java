package com.java.urlshortener.dao;

import com.java.urlshortener.model.URLData;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ghegde on 6/25/17.
 */
public class InMemoryDataStoreDaoTest {
    InMemoryDataStoreDao testClass = new InMemoryDataStoreDao();

    @Test
    public void testStoreAndRetrieve(){
        URLData data = new URLData("qwerty1", "www.abc.com");
        testClass.store(data);
        URLData responseData = testClass.retrieve(data.getKey());
        assertEquals(data, responseData);
    }
}
