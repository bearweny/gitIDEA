package com.lsy.common;

import lombok.Data;
import org.springframework.boot.SpringApplication;
@Data
public class R {
    //状态码  200 是成功， 非200 失败
    private Integer code = 200;
    //状态  true 成功  false 失败
    private Boolean success = true;
    //消息
    private String message;
    //返回的数据
    private Object data;

    /**
     * 成功
     * @param message 提示信息
     * @return
     */
    public static R success(String message) {
        R r = new R();
        r.message = message;
        return r;
    }

    /**
     * 成功
     * @param message
     * @param data   返回数据
     * @return
     */
    public static R success(String message,Object data) {
        R r = success(message);
        r.data = data;
        return r;
    }

    /**
     * 失败
     * @param message
     * @return
     */
    public static R error(String message) {
        R r = new R();
        r.code = 500;
        r.success = false;
        r.message = message;
        return r;
    }
}

