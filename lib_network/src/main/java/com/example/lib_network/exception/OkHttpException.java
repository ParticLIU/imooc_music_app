package com.example.lib_network.exception;

/**
 * @author xiaoyu.liu
 * create at 2020/4/2
 * description: 自定义异常类，返回ecode、eMsg到业务层
 */
public class OkHttpException {

    private int ecode;
    private Object eMsg;

    public OkHttpException(int ecode, Object eMsg) {
        this.ecode = ecode;
        this.eMsg = eMsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Object geteMsg() {
        return eMsg;
    }
}
