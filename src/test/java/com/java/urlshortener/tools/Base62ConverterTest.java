package com.java.urlshortener.tools;

import com.java.urlshortener.tools.Base62Converter;
import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ghegde on 6/25/17.
 */
public class Base62ConverterTest {
    private Base62Converter testClass = new Base62Converter();

    @Test
    public void testGetRandomChar(){
        char random1 = testClass.getRandomChar();
        char random2 = testClass.getRandomChar();
        char random3 = testClass.getRandomChar();
        char random4 = testClass.getRandomChar();
        char random5 = testClass.getRandomChar();
        //assert all are not same to ensure they are random
        assertFalse((random1 == random2) && (random2 == random3) && (random3 == random4) && (random4 == random5));
    }
}
