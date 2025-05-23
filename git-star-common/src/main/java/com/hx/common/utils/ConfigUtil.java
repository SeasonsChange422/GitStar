package com.hx.common.utils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author dhx
 * @date 2025/1/8 18:39
 */
public class ConfigUtil {
    private static Properties props;
    private static final String FILE_URL = "E:\\毕设\\2\\GitStar\\git-star-application\\src\\main\\resources\\config.properties";
    private static void init() throws Exception {
        props = new Properties();
        props.load(new FileInputStream(FILE_URL));
    }

    public static String getValue(String key){
        if(props==null) {
            try {
                init();
                return props.getProperty(key);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return props.getProperty(key);
    }

}
