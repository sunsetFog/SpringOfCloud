package com.upload.common.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class oConvertUtils {
    public static boolean isNotEmpty(Object object) {
        if (object != null && !"".equals(object) && !object.equals("null")) {
            return (true);
        }
        return (false);
    }
}
