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

    @Test
    public void loadUnHitTest() {
        ConfigLoader loader = new ConfigLoader(null,"test");
        loader.getProperties().clear();
        String path = "/" + this.getClass().getCanonicalName().replaceAll("\\.", "/") + "/" + "unhit" + "/";
        loader.loadFunctionProperties(path,"","nohit");
        assertTrue(loader.getProperties().isEmpty());
    }

    @Test
    public void loadHitTest() {
        ConfigLoader loader = new ConfigLoader(null,"test");
        loader.getProperties().clear();
        String path = "/" + this.getClass().getCanonicalName().replaceAll("\\.", "/") + "/" + "loadTest" + "/";
        loader.loadFunctionProperties(path,"loadFunc","");
        assertFalse(loader.getProperties().isEmpty());
        assertThat(loader.getProperties().getProperty("test.value"), is("test"));
    }



}
