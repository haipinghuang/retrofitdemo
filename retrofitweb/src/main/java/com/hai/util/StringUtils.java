package com.hai.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 黄海 on 2017/3/17.
 */
public class StringUtils {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static boolean isEmpty(String... strs) {
        for (String s : strs) {
            if (s.equals("") || null == s) {
                return true;
            }
        }
        return false;
    }
}
