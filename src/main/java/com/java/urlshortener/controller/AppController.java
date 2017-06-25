package com.java.urlshortener.controller;

import com.java.urlshortener.model.URLData;
import com.java.urlshortener.service.URLShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by ghegde on 6/25/17.
 */
@RestController
public class AppController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);
    @Autowired
    private URLShortenerService service ;

    /**
     * This method will give shortened URL for given url
     * @param url
     * @return
     */
    @RequestMapping(value = "/shorten", method = RequestMethod.PUT)
    public String getShortenedUrl(@RequestParam("url") String url,
                                    HttpServletResponse response){
        LOGGER.debug("Request: Shorten url for input url {}", url);

        String key = service.shorten(url);
        if(key==null || key.isEmpty()){
            LOGGER.error("couldn't store data for url {}", url);
            response.setStatus(500);
        }

        LOGGER.debug("Response: Shortened url for input url {} - key {}", url, key);
        return key;
    }

    /**
     * This method will give original URL from given shortened unique key
     * @param uniqueKey
     * @param response
     * @return
     */
    @RequestMapping(value = "/{uniqueId}", method = RequestMethod.GET)
    public String getOriginalUrl(@PathVariable("uniqueId") String uniqueKey,
                                 HttpServletResponse response){
        LOGGER.debug("Request: Retrieve url for key {}", uniqueKey);

        URLData data = service.getLongUrl(uniqueKey);
        //if data is not found, send 404 error status
        if(data == null || data.getUrl().isEmpty()){
            LOGGER.warn("couldn't find data for key {}. Data={}", uniqueKey, data);
            response.setStatus(404);
            return null;
        }

        LOGGER.debug("Response: Retrieved url for key {} - url {}", uniqueKey, data.getUrl());
        return data.getUrl();
    }
}
