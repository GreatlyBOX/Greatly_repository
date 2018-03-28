package com.n22;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Mr on 2017/12/27.
 * 工具类，把Set方法私有化
 * 以防止业务代码修改
 */
@Component
public class AppYml {

private static String maxSize;
private static String minSize;
private static String uuid;

    public static String getUuid() {
        return uuid;
    }
    @Value("${app.property.uuid}")
    public void setUuid(String uuid) {
        AppYml.uuid = uuid;
    }

    public static String getMaxSize() {
        return maxSize;
    }
    @Value("${app.property.maxSize}")
    private void setMaxSize(String maxSize) {
        AppYml.maxSize = maxSize;
    }

    public static String getMinSize() {
        return minSize;
    }
    @Value("${app.property.minSize}")
    private void setMinSize(String minSize) {
        AppYml.minSize = minSize;
    }
}
