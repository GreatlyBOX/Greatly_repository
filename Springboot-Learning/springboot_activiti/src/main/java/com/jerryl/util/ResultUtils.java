package com.jerryl.util;




import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by WYN on 2018/7/31.
 * 用于返回对外接口的json结果
 * 避免频繁创建Map、JSONArray、JSONObject，以及防止代码过于冗长
 */
public class ResultUtils {
    /*
     * Title: returnJSONArray
     * Description: 返回结果为list类型的json格式，没有数据的时候返回JSONArray
     * author WYN
     * date 2018/7/31 16:52
     * @param code
     * @param message
     * @param data
     * return java.util.Map<java.lang.String,java.lang.Object>    返回类型
     * throws
     */
    public static JSONObject returnJSONArray(String code, String message, List data){
        JSONObject json = new JSONObject();
        json.put("data",null == data ? new JSONArray() : data);
        json.put("code",code);
        json.put("message",message);
        return json;
    }
    /*
     * Title: returnJSONObject
     * Description: 返回结果为map类型的json格式，没有数据的时候返回JSONObject
     * author WYN
     * date 2018/7/31 16:53
     * @param code
     * @param message
     * @param data
     * return java.util.Map<java.lang.String,java.lang.Object>    返回类型
     * throws
     */
    public static JSONObject returnJSONObject(String code, String message, Map data){
        JSONObject json = new JSONObject();
        json.put("data",null == data ? new JSONObject() : data);
        json.put("code",code);
        json.put("message",message);
        return json;
    }
}
