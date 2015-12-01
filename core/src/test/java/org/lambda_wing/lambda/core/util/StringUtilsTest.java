package org.lambda_wing.lambda.core.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: makotan
 * Date: 2015/11/08
 */
public class StringUtilsTest {
    
    @Test
    public void emptyTest() {
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(StringUtils.isEmpty(""));
        assertFalse(StringUtils.isEmpty("a"));

        assertFalse(StringUtils.isNotEmpty(null));
        assertFalse(StringUtils.isNotEmpty(""));
        assertTrue(StringUtils.isNotEmpty("a"));
    }
    
    @Test
    public void equalsTest() {
        assertTrue(StringUtils.equals(null,null));

        assertFalse(StringUtils.equals(null,""));
        assertFalse(StringUtils.equals("",null));
        assertFalse(StringUtils.equals(null,"a"));
        assertFalse(StringUtils.equals("b",null));

        assertFalse(StringUtils.equals("a","b"));
        assertTrue(StringUtils.equals("a","a"));
    }
    
}
