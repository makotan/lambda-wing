package com.makotan.libs.lambda.wing.core.util;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by makotan on 2015/10/21.
 */
public class ConfigLoaderTest {
    @Test
    public void loadConfig() {
        ConfigLoader loader = new ConfigLoader(ConfigLoaderTest.class,"test");
        assertFalse(loader.getProperties().isEmpty());
    }
}
