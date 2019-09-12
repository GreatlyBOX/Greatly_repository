package com.n22;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Package com.n22
 * @Author miaopeng
 * @Date 2019/07/05 16:01
 */
public class ResultObject {
    private String msg="OK";
    private Integer status;
    private List list=new ArrayList();

    public ResultObject(Integer status){
        this.status=status;
    }
    public ResultObject(Integer status,Object o){
        this.status=status;
        list.add(o);
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
