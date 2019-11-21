package com.n22.Common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.apache.commons.io.IOUtils;
import java.io.IOException;

/**
 * @Description
 * @Package com.n22.Common
 * @Author miaopeng
 * @Date 2019/11/21 19:39
 */
@Component
public class ReadJson {
    @Value("classpath:json/domo.json")
    private Resource demo;

    public JSONObject demo(){
        try {
            String areaData =  IOUtils.toString(demo.getInputStream(), "UTF-8");
            return (JSONObject) JSON.parse(areaData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
