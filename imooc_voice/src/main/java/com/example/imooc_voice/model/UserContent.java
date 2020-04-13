package com.example.imooc_voice.model;

public class UserContent extends BaseModel {

    public String userId; //用户唯一标识符
    public String photoUrl;
    public String name;
    public String tick;
    public String mobile;
    public String platform;

    @Override
    public String toString() {
        return "UserContent{" +
                "userId='" + userId + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", name='" + name + '\'' +
                ", tick='" + tick + '\'' +
                ", mobile='" + mobile + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }
}
