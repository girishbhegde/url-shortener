package com.java.urlshortener.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.urlshortener.model.URLData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by ghegde on 6/25/17.
 */
public class RedisDataStoreDaoTest {
    private RedisDataStoreDao testClass;
    private RedisTemplate<String, Object> redisTemplate;
    private ValueOperations mockValueOp;
    private ObjectMapper objectMapper = new ObjectMapper();
    private URLData data;

    @Before
    public void init(){
        data = new URLData("qwerty1", "www.abc.com");
        testClass = new RedisDataStoreDao();
        redisTemplate = mock(RedisTemplate.class);
        Whitebox.setInternalState(testClass, "redisTemplate", redisTemplate);
        mockValueOp = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(mockValueOp);
    }

    @Test
    public void testStore() throws IOException {
        doNothing().when(mockValueOp).set(data.getKey(), objectMapper.writeValueAsString(data));
        testClass.store(data);
        verify(redisTemplate).opsForValue();
        verify(mockValueOp).set(data.getKey(), objectMapper.writeValueAsString(data));
    }

    @Test
    public void testRetrieve() throws IOException {
        when(mockValueOp.get(data.getKey())).thenReturn(objectMapper.writeValueAsString(data));
        URLData response = testClass.retrieve("qwerty1");
        assertEquals("www.abc.com", response.getUrl());
        assertEquals("qwerty1", response.getKey());
        verify(redisTemplate).opsForValue();
        verify(mockValueOp).get(data.getKey());
    }
}
