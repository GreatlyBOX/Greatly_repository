package com.n22.Controller;

import com.alibaba.fastjson.JSONObject;
import com.n22.Common.ReadJson;
import com.n22.Model.BaseModel;
import com.n22.Model.User;
import com.n22.Common.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mr on 2017/12/25.
 *测试Controller
 */
@RestController
public class TestController {
    @Autowired
    ReadJson readJson;
    @RequestMapping("/Test")
    public BaseModel test(){
        BaseModel baseModel=null;
        try {
            User user=new User();
            user.setAge(null);
            user.setId(UUID.randomUUID().toString());
            user.setBirthday(new Date());
            user.setName("小明");
            List list=new ArrayList<>();
            list.add(user);
            baseModel=new BaseModel(HttpUtil.successCode,"返回成功！",true,list);
        }catch (Exception e){
            e.printStackTrace();
            baseModel=new BaseModel(HttpUtil.failCode,"返回失败！",true,null);
        }

        return baseModel;

    }


    @RequestMapping("/json")
    public JSONObject json(){
        return readJson.demo();
    }
}
