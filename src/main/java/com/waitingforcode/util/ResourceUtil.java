package com.waitingforcode.util;

import java.io.InputStream;

public final class ResourceUtil {

    private ResourceUtil() {
        // prevents init
    }

    public static InputStream loadResourceStream(String file) {
        return ResourceUtil.class.getClassLoader().getResourceAsStream(file);
    }

}
