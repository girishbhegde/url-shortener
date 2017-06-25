package com.java.urlshortener.service;

import com.java.urlshortener.dao.DataStoreDao;
import com.java.urlshortener.model.URLData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.core.env.Environment;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by ghegde on 6/25/17.
 */
public class URLShortenerServiceTest {
    private URLShortenerService testClass;
    private DataStoreDao mockMemoryDataStore;
    private DataStoreDao mockRedisDataStore;
    private Environment env;
    private URLData mockData;

    @Before
    public void init(){
        mockData = new URLData("qwerty1","http://www.abc.com");
        testClass = new URLShortenerService();
        mockMemoryDataStore = mock(DataStoreDao.class);
        Whitebox.setInternalState(testClass, "memoryDataStore", mockMemoryDataStore);
        mockRedisDataStore = mock(DataStoreDao.class);
        Whitebox.setInternalState(testClass, "redisDataStore", mockRedisDataStore);
        env = mock(Environment.class);
        Whitebox.setInternalState(testClass, "env", env);
    }

    @Test
    public void testShortenWithRedis() throws IOException {
        when(env.getProperty("datastore.type")).thenReturn("redis");
        doNothing().when(mockRedisDataStore).store(any(URLData.class));
        when(env.getProperty("server.port")).thenReturn("8080");
        testClass.init();
        String response = testClass.shorten("http://www.abc.com");
        assertTrue(response.startsWith("http://localhost:8080"));
        String uniqueKey = response.substring("http://localhost:8080/".length());
        assertEquals(7, uniqueKey.length());
        verify(env).getProperty("datastore.type");
        verify(mockRedisDataStore).store(any(URLData.class));
        verify(env).getProperty("server.port");
    }

    @Test
    public void testShortenWithRedisIOException() throws IOException {
        when(env.getProperty("datastore.type")).thenReturn("redis");
        doThrow(new IOException("test")).when(mockRedisDataStore).store(any(URLData.class));
        when(env.getProperty("server.port")).thenReturn("8080");
        testClass.init();
        String response = testClass.shorten("http://www.abc.com");
        assertNull(response);
        verify(env).getProperty("datastore.type");
        verify(mockRedisDataStore).store(any(URLData.class));
    }

    @Test
    public void testShortenWithInMemory() throws IOException {
        doNothing().when(mockMemoryDataStore).store(any(URLData.class));
        when(env.getProperty("server.port")).thenReturn("8080");
        testClass.init();
        String response = testClass.shorten("http://www.abc.com");
        assertTrue(response.startsWith("http://localhost:8080"));
        String uniqueKey = response.substring("http://localhost:8080/".length());
        assertEquals(7, uniqueKey.length());
        verify(env).getProperty("datastore.type");
        verify(mockMemoryDataStore).store(any(URLData.class));
        verify(env).getProperty("server.port");
    }

    @Test
    public void testShortenWithInMemoryIOException() throws IOException {
        doThrow(new IOException("test")).when(mockMemoryDataStore).store(any(URLData.class));
        when(env.getProperty("server.port")).thenReturn("8080");
        testClass.init();
        String response = testClass.shorten("http://www.abc.com");
        assertNull(response);
        verify(env).getProperty("datastore.type");
        verify(mockMemoryDataStore).store(any(URLData.class));
    }

    @Test
    public void testGetLongUrlInMemory() throws IOException {
        when(mockMemoryDataStore.retrieve("http://localhsot:8080/qwerty1")).thenReturn(mockData);
        testClass.init();
        URLData response = testClass.getLongUrl("http://localhsot:8080/qwerty1");
        assertEquals(mockData, response);
        verify(mockMemoryDataStore).retrieve("http://localhsot:8080/qwerty1");
    }

    @Test
    public void testGetLongUrlInMemoryIOException() throws IOException {
        when(mockMemoryDataStore.retrieve("http://localhsot:8080/qwerty1")).thenThrow(new IOException());
        testClass.init();
        URLData response = testClass.getLongUrl("http://localhsot:8080/qwerty1");
        assertNull(response);
        verify(mockMemoryDataStore).retrieve("http://localhsot:8080/qwerty1");
    }

    @Test
    public void testGetLongUrlRedis() throws IOException {
        when(env.getProperty("datastore.type")).thenReturn("redis");
        when(mockRedisDataStore.retrieve("http://localhsot:8080/qwerty1")).thenReturn(mockData);
        testClass.init();
        URLData response = testClass.getLongUrl("http://localhsot:8080/qwerty1");
        assertEquals(mockData, response);
        verify(mockRedisDataStore).retrieve("http://localhsot:8080/qwerty1");
        verify(env).getProperty("datastore.type");
    }

    @Test
    public void testGetLongUrlRedisIOException() throws IOException {
        when(env.getProperty("datastore.type")).thenReturn("redis");
        when(mockRedisDataStore.retrieve("http://localhsot:8080/qwerty1")).thenThrow(new IOException());
        testClass.init();
        URLData response = testClass.getLongUrl("http://localhsot:8080/qwerty1");
        assertNull(response);
        verify(mockRedisDataStore).retrieve("http://localhsot:8080/qwerty1");
        verify(env).getProperty("datastore.type");
    }

}
