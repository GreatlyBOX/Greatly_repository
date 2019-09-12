package com.n22.data;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

/**
 * @Description
 * @Package com.n22.data
 * @Author miaopeng
 * @Date 2019/07/05 11:26
 */
@Measurement(name = "logInfo")
public class LogInfo {

    // Column中的name为measurement中的列名
    // 此外,需要注意InfluxDB中时间戳均是以UTC时保存,在保存以及提取过程中需要注意时区转换
    @Column(name = "time")
    private String time;
    // 注解中添加tag = true,表示当前字段内容为tag内容
    @Column(name = "hostname", tag = true)
    private String hostname;
    @Column(name = "value", tag = true)
    private String value;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
