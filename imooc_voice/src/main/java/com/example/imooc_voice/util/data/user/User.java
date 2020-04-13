package com.example.imooc_voice.util.data.user;

import com.example.imooc_voice.model.BaseModel;
import com.example.imooc_voice.model.UserContent;

public class User extends BaseModel {

    public int ecode;
    public String emsg;
    public UserContent data;

    @Override
    public String toString() {
        return "User{" +
                "ecode=" + ecode +
                ", emsg='" + emsg + '\'' +
                ", data=" + data +
                '}';
    }
}
