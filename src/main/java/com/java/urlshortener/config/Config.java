package com.java.urlshortener.config;

import com.java.urlshortener.dao.DataStoreDao;
import com.java.urlshortener.dao.RedisDataStoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.stereotype.Repository;

/**
 * Created by ghegde on 6/25/17.
 */
@Configuration
public class Config {
    @Autowired
    private Environment env;

    /**
     * Bean definition for redis jedis connection factory
     * @return
     */
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        if("redis".equals(env.getProperty("datastore.type"))){
            String host = env.getProperty("redis.host");
            String port = env.getProperty("redis.port");
            String password = env.getProperty("redis.password");
            jedisConFactory.setHostName(host);
            jedisConFactory.setPort(Integer.parseInt(port));
            jedisConFactory.setPassword(password);
        }
        return jedisConFactory;
    }

    /**
     * Bean for creating redis template
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }
}
